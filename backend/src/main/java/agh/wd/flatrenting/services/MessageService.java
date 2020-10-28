package agh.wd.flatrenting.services;


import agh.wd.flatrenting.database.ConversationRepository;
import agh.wd.flatrenting.database.OfferRepository;
import agh.wd.flatrenting.database.UserRepository;
import agh.wd.flatrenting.entities.Conversation;
import agh.wd.flatrenting.entities.Message;
import agh.wd.flatrenting.entities.Offer;
import agh.wd.flatrenting.entities.User;
import agh.wd.flatrenting.exceptions.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
public class MessageService {

    private static final Logger logger = Logger.getLogger(MessageService.class);


    private final UserRepository userRepository;
    private final OfferRepository offerRepository;
    private final ConversationRepository conversationRepository;
    private final EntityManager entityManager;

    @Autowired
    public MessageService(UserRepository userRepository,
                          OfferRepository offerRepository,
                          ConversationRepository conversationRepository,
                          EntityManager entityManager) {
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
        this.conversationRepository = conversationRepository;
        this.entityManager = entityManager;
    }

    public Message getLastMessageForConversation(Integer conversationId) {
        Conversation conversation = conversationRepository.findById(conversationId).orElse(null);

        if(conversation == null) {
            logger.error("Conversation " + conversationId + " not found.");
            throw new ConversationNotFoundException(conversationId.toString());
        }

        List<Message> messages = conversation.getMessages();
        messages.sort(Comparator.comparing(Message::getCreationTimestamp));

        return messages.get(0);
    }

    public Collection<Conversation> getAllForUser(String nick) {
        User user = userRepository.findByNick(nick).orElse(null);

        if(user == null) {
            logger.error("User " + nick + " not found.");
            throw new UserNotFoundException(nick);
        } else {
            return conversationRepository.findAllByUser(user);
        }
    }

    public List<List<Conversation>> getAllConversationsForOfferIds(List<Integer> offerIds) {
        List<List<Conversation>> conversationsListList = new ArrayList<>();
        offerIds.forEach(
                id -> conversationsListList.add(getAllForOffer(id))
        );

        return conversationsListList;
    }

    private List<Conversation> getAllForOffer(Integer offerId) {
        Offer offer = offerRepository.findById(offerId).orElse(null);

        if(offer == null) {
            logger.error("Offer " + offerId + " not found.");
            throw new OfferNotFoundException(offerId.toString());
        } else {
            return conversationRepository.findAllByOffer(offer);
        }
    }

    @Transactional
    public Conversation addMessage(String messageText, String senderNick, Integer conversationId) {
        User sender = userRepository.findByNick(senderNick).orElse(null);
        Conversation conversation = conversationRepository.findById(conversationId).orElse(null);

        if(sender == null) {
            logger.error("User " + senderNick + " not found.");
            throw new UserNotFoundException(senderNick);
        } else if(conversation == null) {
            logger.error("Conversation " + conversationId + " not found.");
            throw new ConversationNotFoundException(conversationId.toString());
        } else {
            Message message = new Message();
            if(conversation.getUser().getNick().equals(senderNick)) {
                message.setWasReadBySecondUser(true);
                message.setWasReadByOfferOwner(false);
            } else {
                message.setWasReadBySecondUser(false);
                message.setWasReadByOfferOwner(true);
            }
            message.setContent(messageText);
            message.setSender(sender);

            User offerOwner = conversation.getOffer().getOwner();
            if (sender.equals(offerOwner)) {
                message.setReceiver(conversation.getUser());
            } else {
                message.setReceiver(offerOwner);
            }

            message.setConversation(conversation);
            List<Message> messages = conversation.getMessages();
            messages.add(message);
            conversation.setMessages(messages);

            entityManager.persist(conversation);

            return conversation;
        }
    }

    @Transactional
    public Conversation createConversation(String messageText, String senderNick, Integer offerId) {
        User sender = userRepository.findByNick(senderNick).orElse(null);
        Offer offer = offerRepository.findById(offerId).orElse(null);

        if(sender == null) {
            logger.error("User " + senderNick + " not found.");
            throw new UserNotFoundException(senderNick);
        } else if(offer == null) {
            logger.error("Offer " + offerId + " not found.");
            throw new OfferNotFoundException(offerId.toString());
        } else if(conversationRepository.existsByOfferAndUser(offer, sender)) {
            logger.error("Conversation already exists.");
            throw new ConversationExistsException();
        } else {
            Message message = new Message();
            message.setWasReadBySecondUser(true);
            message.setWasReadByOfferOwner(false);
            message.setContent(messageText);
            message.setReceiver(offer.getOwner());
            message.setSender(sender);

            Conversation conversation = new Conversation();
            conversation.setUser(sender);
            conversation.setOffer(offer);
            conversation.setMessages(List.of(message));
            message.setConversation(conversation);

            entityManager.persist(conversation);

            return conversation;
        }
    }

    @Transactional
    public Conversation markAsRead(String userNick, Integer conversationId) {
        Conversation conversation = conversationRepository.getById(conversationId)
                .orElseThrow(() -> new ConversationNotFoundException(conversationId.toString()));

        if(!userNick.equals(conversation.getUser().getNick()) && !userNick.equals(conversation.getOffer().getOwner().getNick())) {
            throw new NotAuthorizedException();
        }

        List<Message> messages = conversation.getMessages();
        messages.forEach(message -> {
            if(conversation.getUser().getNick().equals(userNick)) {
                message.setWasReadBySecondUser(true);
            } else {
                message.setWasReadByOfferOwner(true);
            }
        });
        conversation.setMessages(messages);
        conversationRepository.save(conversation);
        return conversation;
    }
}

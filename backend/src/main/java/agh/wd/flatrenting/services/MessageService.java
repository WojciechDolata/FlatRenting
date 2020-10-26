package agh.wd.flatrenting.services;


import agh.wd.flatrenting.database.ConversationRepository;
import agh.wd.flatrenting.database.MessageRepository;
import agh.wd.flatrenting.database.OfferRepository;
import agh.wd.flatrenting.database.UserRepository;
import agh.wd.flatrenting.entities.Conversation;
import agh.wd.flatrenting.entities.Message;
import agh.wd.flatrenting.entities.Offer;
import agh.wd.flatrenting.entities.User;
import agh.wd.flatrenting.exceptions.ConversationExistsException;
import agh.wd.flatrenting.exceptions.ConversationNotFoundException;
import agh.wd.flatrenting.exceptions.OfferNotFoundException;
import agh.wd.flatrenting.exceptions.UserNotFoundException;
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

    private static Logger logger = Logger.getLogger(MessageService.class);


    private MessageRepository messageRepository;
    private UserRepository userRepository;
    private OfferRepository offerRepository;
    private ConversationRepository conversationRepository;
    private OfferService offerService;
    private EntityManager entityManager;

    @Autowired
    public MessageService(MessageRepository messageRepository,
                          UserRepository userRepository,
                          OfferRepository offerRepository,
                          OfferService offerService,
                          ConversationRepository conversationRepository,
                          EntityManager entityManager) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
        this.offerService = offerService;
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
        } else if(conversationRepository.existsByOfferAndAndUser(offer, sender)) {
            logger.error("Conversation already exists.");
            throw new ConversationExistsException();
        } else {
            Message message = new Message();
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
}
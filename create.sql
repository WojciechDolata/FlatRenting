create sequence hibernate_sequence start 1 increment 1
create table fr01_offer (id int4 not null, creation_tsp timestamp, description varchar(1023), location varchar(255), locationx float8, locationy float8, price int4, room_count int4, flat_size int4, title varchar(255), visit_count int4, owner_id int4, primary key (id))
create table fr01_offer_conversations (offer_id int4 not null, conversations_id int4 not null)
create table fr01_offer_photos (offer_id int4 not null, photos_id int4 not null)
create table fr02_photo (id int4 not null, creation_tsp timestamp, data bytea, name varchar(255), origin_id int4, primary key (id))
create table fr03_user (id int4 not null, creation_tsp timestamp, nick varchar(255), phone_number varchar(255), primary key (id))
create table fr03_user_conversations (user_id int4 not null, conversations_id int4 not null)
create table fr03_user_offers (user_id int4 not null, offers_id int4 not null)
create table fr04_conversation (id int4 not null, creation_tsp timestamp, offer_id int4, user_id int4, primary key (id))
create table fr05_message (id int4 not null, content varchar(255), creation_tsp timestamp, was_read_by_offer_owner boolean, was_read_by_second_user boolean, conversation_id int4, receiver_id int4, sender_id int4, primary key (id))
create table fr06_user_pref (id int4 not null, creation_tsp timestamp, location varchar(255), max_days_ago int4, max_number_of_rooms int4, max_price int4, max_size int4, min_number_of_rooms int4, min_price int4, min_size int4, nick varchar(255), primary key (id))
create table fr99_user_cred (id int4 not null, creation_tsp timestamp, email varchar(255), nick varchar(255), password_hash varchar(255), primary key (id))
alter table fr01_offer_conversations add constraint UK_86489ove0pjgy5yqc7qej5cvx unique (conversations_id)
alter table fr01_offer_photos add constraint UK_no6p4ewdelat4xi4w12vddh7f unique (photos_id)
alter table fr03_user add constraint UK_sbrn7waigllvva5tsav8sv9r7 unique (nick)
alter table fr03_user_conversations add constraint UK_p1hnkt3m29r15a6jhr8hx43db unique (conversations_id)
alter table fr03_user_offers add constraint UK_a0ycps2a9c28tm3y6yskn241v unique (offers_id)
alter table fr06_user_pref add constraint UK_2sgstu1o056tkjb761npksrwe unique (nick)
alter table fr99_user_cred add constraint UK_8ujeldhrv34i4u7h9i5phxicf unique (email)
alter table fr99_user_cred add constraint UK_9pm8q732ih6mo9ie0yobsb1ue unique (nick)
alter table fr01_offer add constraint FK344gf585huqm5scr9glvos7m6 foreign key (owner_id) references fr03_user
alter table fr01_offer_conversations add constraint FKlbid5ewpy94utphq2xbgk47ii foreign key (conversations_id) references fr04_conversation
alter table fr01_offer_conversations add constraint FKbowdoxtvu8c0blhp3bnp0keod foreign key (offer_id) references fr01_offer
alter table fr01_offer_photos add constraint FKcpfvuxac4eofrw0w9c9rdt03c foreign key (photos_id) references fr02_photo
alter table fr01_offer_photos add constraint FKncenpn9w9i9hod756ydadys9w foreign key (offer_id) references fr01_offer
alter table fr02_photo add constraint FKps9a2mcbru8d158cgtwbcxj37 foreign key (origin_id) references fr01_offer
alter table fr03_user_conversations add constraint FKre8sy35yonufgn01kqxffqui8 foreign key (conversations_id) references fr04_conversation
alter table fr03_user_conversations add constraint FKgvt57818q71wckp2p65aux3rt foreign key (user_id) references fr03_user
alter table fr03_user_offers add constraint FKgaf4fm3m3b5v52bu1etx4q4hh foreign key (offers_id) references fr01_offer
alter table fr03_user_offers add constraint FK1kru9mrj4kv3o0eso11xh4jng foreign key (user_id) references fr03_user
alter table fr04_conversation add constraint FK6qtad61y8ariln6qmn28sa5qe foreign key (offer_id) references fr01_offer
alter table fr04_conversation add constraint FKm53id99eqhq33i77532o408po foreign key (user_id) references fr03_user
alter table fr05_message add constraint FKflbtlq2afl4s6fa6e2ps49cc4 foreign key (conversation_id) references fr04_conversation
alter table fr05_message add constraint FK39dx7y8x9lfercxemrg7pit77 foreign key (receiver_id) references fr03_user
alter table fr05_message add constraint FKcr15bbwvckokv52v4eeqvcfiw foreign key (sender_id) references fr03_user

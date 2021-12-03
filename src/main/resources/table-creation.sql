drop table if exists flashcards;
drop table if exists app_users;

create table app_users (
    user_id varchar check (user_id <> ''),
    first_name varchar(25) not null check (first_name <> ''),
    last_name varchar(25) not null check (last_name <> ''),
    email varchar(255) unique not null check (email <> ''),
    username varchar(20) unique not null check (username <> ''),
    password varchar(255) not null check (password <> ''),

    constraint app_users_pk
    primary key (user_id)
);

create table flashcards (
    card_id varchar check (card_id <> ''),
    question_text varchar not null check (question_text <> ''),
    answer_text varchar not null check (answer_text <> ''),
    creator_id varchar not null check (creator_id <> ''),

    constraint flashcards_pk
    primary key (card_id),

    constraint flashcard_creator_fk
    foreign key (creator_id)
    references app_users
);

create table study_sets (
    study_set_id varchar check (study_set_id <> ''),
    set_name varchar check (set_name <> ''),
    owner_id varchar check (owner_id <> ''),

    constraint study_sets_pk
    primary key (study_set_id),

    constraint study_set_owner_fk
    foreign key (owner_id)
    references app_users
);

create table study_set_cards (
    study_set_id varchar check (study_set_id <> ''),
    card_id varchar check (card_id <> ''),

    constraint study_set_cards_pk
    primary key (study_set_id, card_id),

    constraint study_set_fk
    foreign key (study_set_id)
    references study_sets,

    constraint card_fk
    foreign key (card_id)
    references flashcards

)


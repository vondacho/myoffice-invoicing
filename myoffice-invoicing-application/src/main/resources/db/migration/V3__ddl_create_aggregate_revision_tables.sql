create table t_folder_aud (
pk_id bigint not null,
rev integer not null,
revtype tinyint,
asked_amount varchar(255),
debt_amount varchar(255),
id varchar(255),
payed_amount varchar(255),
provisioned_amount varchar(255),
primary key (pk_id, rev)
);

create table t_folder_affiliate_aud (
rev integer not null,
fk_folder bigint not null,
customer_id varchar(255) not null,
primary_debtor boolean not null,
revtype tinyint,
primary key (rev, fk_folder, customer_id, primary_debtor)
);

create table t_folder_ticket_aud (
rev integer not null,
fk_folder bigint not null,
tickets varchar(255) not null,
revtype tinyint,
primary key (rev, fk_folder, tickets)
);

create table t_debt_aud (
pk_id bigint not null,
rev integer not null,
revtype tinyint,
amount varchar(255),
cart_amount varchar(255),
cart_id varbinary(255),
delay_date date,
delay_day_count integer,
discount_rate varchar(255),
folder_id varchar(255),
id varchar(255),
notes varchar(255),
payed_amount varchar(255),
status varchar(255),
tax_rate varchar(255),
primary key (pk_id, rev)
);

create table t_debt_payment_aud (
rev integer not null,
fk_debt bigint not null,
amount bigint not null,
date date not null,
ticket varchar(255) not null,
revtype tinyint,
primary key (rev, fk_debt, amount, date, ticket)
);

create table t_debt_recall_aud (
rev integer not null,
fk_debt bigint not null,
amount bigint not null,
date date not null,
revtype tinyint,
primary key (rev, fk_debt, amount, date)
);

alter table t_debt_aud
add constraint FKtg9ol8c0q16nkd5btmg2nahsg
foreign key (rev)
references t_revinfo;

alter table t_debt_payment_aud
add constraint FKd01dgbjvko7bidm1nc0ldegn3
foreign key (rev)
references t_revinfo;

alter table t_debt_recall_aud
add constraint FKarxok6ab68tf177fp7lkh111f
foreign key (rev)
references t_revinfo;

alter table t_folder_affiliate_aud
add constraint FKptfriegou9b0sq9vlxkke6xrh
foreign key (rev)
references t_revinfo;

alter table t_folder_aud
add constraint FKnkalbhakiej3madk3w48c5k1o
foreign key (rev)
references t_revinfo;

alter table t_folder_ticket_aud
add constraint FKev0cgdab16s0qcj5jibgs3j6x
foreign key (rev)
references t_revinfo;
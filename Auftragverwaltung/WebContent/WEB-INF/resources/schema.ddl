
    create table `Resource` (
        name varchar(255) not null,
        primary key (name)
    ) ENGINE=InnoDB;

    create table `Role` (
        name varchar(255) not null,
        primary key (name)
    ) ENGINE=InnoDB;

    create table `User` (
        username varchar(255) not null,
        password tinyblob not null,
        primary key (username)
    ) ENGINE=InnoDB;

    create table roleresourcenm (
        `resource` varchar(255) not null,
        `role` varchar(255) not null,
        primary key (`role`, `resource`)
    ) ENGINE=InnoDB;

    create table rolerolenm (
        childrole varchar(255) not null,
        parentrole varchar(255) not null,
        primary key (parentrole, childrole)
    ) ENGINE=InnoDB;

    create table userrolenm (
        `user` varchar(255) not null,
        `role` varchar(255) not null,
        primary key (`role`, `user`)
    ) ENGINE=InnoDB;

    alter table roleresourcenm 
        add constraint FK_g7kpun69oorakk361y3xe454f 
        foreign key (`role`) 
        references `Role` (name);

    alter table roleresourcenm 
        add constraint FK_2nmu0urlnlwb6or7pv4l2b37d 
        foreign key (`resource`) 
        references `Resource` (name);

    alter table rolerolenm 
        add constraint FK_2ktivh97rgpc1mqhw7vjsg4g8 
        foreign key (parentrole) 
        references `Role` (name);

    alter table rolerolenm 
        add constraint FK_2vftx3kns1if099i5g439ia95 
        foreign key (childrole) 
        references `Role` (name);

    alter table userrolenm 
        add constraint FK_5b4vuo6yxfybqc0cf2jjll5kf 
        foreign key (`role`) 
        references `Role` (name);

    alter table userrolenm 
        add constraint FK_16evy309uhevxrg5da2w2qow8 
        foreign key (`user`) 
        references `User` (username);

    create table gabrieltransport.user (
        username varchar(255) not null,
        password tinyblob not null,
        primary key (username)
    ) ENGINE=InnoDB;

    create table gabrieltransport.userrolenm (
        user varchar(255) not null,
        role varchar(255) not null,
        primary key (user, role)
    ) ENGINE=InnoDB;

    alter table gabrieltransport.anhaenger 
        drop constraint UKrhpn6d2c2u8a6awldvg068gd;

    alter table gabrieltransport.anhaenger 
        add constraint UKrhpn6d2c2u8a6awldvg068gd unique (Nummer);

    create table `RESOURCE` (
        `NAME` varchar(255) not null,
        primary key (`NAME`)
    ) ENGINE=InnoDB;

    create table `ROLE` (
        `NAME` varchar(255) not null,
        primary key (`NAME`)
    ) ENGINE=InnoDB;

    create table ROLERESOURCENM (
        `RESOURCE` varchar(255) not null,
        `ROLE` varchar(255) not null,
        primary key (`ROLE`, `RESOURCE`)
    ) ENGINE=InnoDB;

    create table ROLEROLENM (
        CHILDROLE varchar(255) not null,
        PARENTROLE varchar(255) not null,
        primary key (PARENTROLE, CHILDROLE)
    ) ENGINE=InnoDB;

    alter table gabrieltransport.anhaenger 
        add constraint FKmhd7atdc23uhv74xufyumoihe 
        foreign key (fkTyp) 
        references gabrieltransport.anhaengertyp (idanhaengertyp);

    alter table gabrieltransport.anhaenger 
        add constraint FKi026kuw3m8jbyi6w410fgpkv 
        foreign key (fkFunktion) 
        references gabrieltransport.fahrzeug_funktion (idfunktion);

    alter table gabrieltransport.fahrerauftrag 
        add constraint FKg53d9ohfj5863iqlcwmq47xvq 
        foreign key (fkauftrag) 
        references gabrieltransport.auftrag (idAuftrag);

    alter table gabrieltransport.fahrerauftrag 
        add constraint FKbobmsyoclqb1jgew1rblt00kc 
        foreign key (fkFahrer) 
        references gabrieltransport.fahrer (idfahrer);

    alter table gabrieltransport.fahrerfunktionmap 
        add constraint FK1jifyfkrc7m0693iqmyiec4ba 
        foreign key (fkFahrer) 
        references gabrieltransport.fahrer (idfahrer);

    alter table gabrieltransport.fahrerfunktionmap 
        add constraint FKd0orph1dxtgikfadww4tk64fq 
        foreign key (fkFunktion) 
        references gabrieltransport.fahrerfunktion (idfunktion);

    alter table gabrieltransport.fahrzeug 
        add constraint FKt90s8ae65yjbd92arcai3274m 
        foreign key (AnhaengerTyp) 
        references gabrieltransport.anhaengertyp (idanhaengertyp);

    alter table gabrieltransport.fahrzeug 
        add constraint FKgkhrh55g5rdlstgc5wuydi4gm 
        foreign key (Funktion) 
        references gabrieltransport.fahrzeug_funktion (idfunktion);

    alter table gabrieltransport.fahrzeugauftrag 
        add constraint FKcmwyyxeos2rel7f7k3p2j0qs1 
        foreign key (fkAnhänger) 
        references gabrieltransport.anhaenger (idanhaenger);

    alter table gabrieltransport.fahrzeugauftrag 
        add constraint FK3phdamshu2tfjsfkfi7gr3g8l 
        foreign key (fkAuftrag) 
        references gabrieltransport.auftrag (idAuftrag);

    alter table gabrieltransport.fahrzeugauftrag 
        add constraint FKomu28sg0e4onsngy4fp54c9fm 
        foreign key (fkFahrzeug) 
        references gabrieltransport.fahrzeug (idfahrzeug);

    alter table gabrieltransport.userrolenm 
        add constraint FKahstt1iavh8i1xqqio0o9hpss 
        foreign key (role) 
        references `ROLE` (`NAME`);

    alter table gabrieltransport.userrolenm 
        add constraint FKsvo9gdqf26n9i7bkdtp5ro67h 
        foreign key (user) 
        references gabrieltransport.user (username);

    alter table ROLERESOURCENM 
        add constraint FKan33knkv6vxyubowoou51i24i 
        foreign key (`ROLE`) 
        references `ROLE` (`NAME`);

    alter table ROLERESOURCENM 
        add constraint FK8bg4vr1hdj04pnre1ilphwaya 
        foreign key (`RESOURCE`) 
        references `RESOURCE` (`NAME`);

    alter table ROLEROLENM 
        add constraint FK5f7bmwos053wnl5yi4ksxwmwo 
        foreign key (PARENTROLE) 
        references `ROLE` (`NAME`);

    alter table ROLEROLENM 
        add constraint FKko3y06p19y83p5oa48c1hemrp 
        foreign key (CHILDROLE) 
        references `ROLE` (`NAME`);

    alter table gabrieltransport.user 
        add column iduser INT not null;

    create table `RESOURCE` (
        `NAME` varchar(255) not null,
        primary key (`NAME`)
    ) ENGINE=InnoDB;

    create table `ROLE` (
        `NAME` varchar(255) not null,
        primary key (`NAME`)
    ) ENGINE=InnoDB;

    create table ROLERESOURCENM (
        `RESOURCE` varchar(255) not null,
        `ROLE` varchar(255) not null,
        primary key (`ROLE`, `RESOURCE`)
    ) ENGINE=InnoDB;

    create table ROLEROLENM (
        CHILDROLE varchar(255) not null,
        PARENTROLE varchar(255) not null,
        primary key (PARENTROLE, CHILDROLE)
    ) ENGINE=InnoDB;

    alter table ROLERESOURCENM 
        add constraint FKan33knkv6vxyubowoou51i24i 
        foreign key (`ROLE`) 
        references `ROLE` (`NAME`);

    alter table ROLERESOURCENM 
        add constraint FK8bg4vr1hdj04pnre1ilphwaya 
        foreign key (`RESOURCE`) 
        references `RESOURCE` (`NAME`);

    alter table ROLEROLENM 
        add constraint FK5f7bmwos053wnl5yi4ksxwmwo 
        foreign key (PARENTROLE) 
        references `ROLE` (`NAME`);

    alter table ROLEROLENM 
        add constraint FKko3y06p19y83p5oa48c1hemrp 
        foreign key (CHILDROLE) 
        references `ROLE` (`NAME`);

    create table gabrieltransport.user (
        iduser INT not null auto_increment,
        password tinyblob not null,
        username varchar(255) not null,
        primary key (iduser)
    ) ENGINE=InnoDB;

    create table gabrieltransport.userrolenm (
        user INT not null,
        role varchar(255) not null,
        primary key (user, role)
    ) ENGINE=InnoDB;

    alter table gabrieltransport.user 
        drop constraint UK_sb8bbouer5wak8vyiiy4pf2bx;

    alter table gabrieltransport.user 
        add constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique (username);

    create table `RESOURCE` (
        `NAME` varchar(255) not null,
        primary key (`NAME`)
    ) ENGINE=InnoDB;

    create table `ROLE` (
        `NAME` varchar(255) not null,
        primary key (`NAME`)
    ) ENGINE=InnoDB;

    create table ROLERESOURCENM (
        `RESOURCE` varchar(255) not null,
        `ROLE` varchar(255) not null,
        primary key (`ROLE`, `RESOURCE`)
    ) ENGINE=InnoDB;

    create table ROLEROLENM (
        CHILDROLE varchar(255) not null,
        PARENTROLE varchar(255) not null,
        primary key (PARENTROLE, CHILDROLE)
    ) ENGINE=InnoDB;

    alter table gabrieltransport.userrolenm 
        add constraint FKahstt1iavh8i1xqqio0o9hpss 
        foreign key (role) 
        references `ROLE` (`NAME`);

    alter table gabrieltransport.userrolenm 
        add constraint FKsvo9gdqf26n9i7bkdtp5ro67h 
        foreign key (user) 
        references gabrieltransport.user (iduser);

    alter table ROLERESOURCENM 
        add constraint FKan33knkv6vxyubowoou51i24i 
        foreign key (`ROLE`) 
        references `ROLE` (`NAME`);

    alter table ROLERESOURCENM 
        add constraint FK8bg4vr1hdj04pnre1ilphwaya 
        foreign key (`RESOURCE`) 
        references `RESOURCE` (`NAME`);

    alter table ROLEROLENM 
        add constraint FK5f7bmwos053wnl5yi4ksxwmwo 
        foreign key (PARENTROLE) 
        references `ROLE` (`NAME`);

    alter table ROLEROLENM 
        add constraint FKko3y06p19y83p5oa48c1hemrp 
        foreign key (CHILDROLE) 
        references `ROLE` (`NAME`);

    create table gabrieltransport.user (
        iduser INT not null auto_increment,
        password tinyblob not null,
        username varchar(255) not null,
        primary key (iduser)
    ) ENGINE=InnoDB;

    create table gabrieltransport.userrolenm (
        user INT not null,
        role varchar(255) not null,
        primary key (user, role)
    ) ENGINE=InnoDB;

    alter table gabrieltransport.user 
        drop constraint UK_sb8bbouer5wak8vyiiy4pf2bx;

    alter table gabrieltransport.user 
        add constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique (username);

    create table `RESOURCE` (
        `NAME` varchar(255) not null,
        primary key (`NAME`)
    ) ENGINE=InnoDB;

    create table `ROLE` (
        `NAME` varchar(255) not null,
        primary key (`NAME`)
    ) ENGINE=InnoDB;

    create table ROLERESOURCENM (
        `RESOURCE` varchar(255) not null,
        `ROLE` varchar(255) not null,
        primary key (`ROLE`, `RESOURCE`)
    ) ENGINE=InnoDB;

    create table ROLEROLENM (
        CHILDROLE varchar(255) not null,
        PARENTROLE varchar(255) not null,
        primary key (PARENTROLE, CHILDROLE)
    ) ENGINE=InnoDB;

    alter table gabrieltransport.userrolenm 
        add constraint FKahstt1iavh8i1xqqio0o9hpss 
        foreign key (role) 
        references `ROLE` (`NAME`);

    alter table gabrieltransport.userrolenm 
        add constraint FKsvo9gdqf26n9i7bkdtp5ro67h 
        foreign key (user) 
        references gabrieltransport.user (iduser);

    alter table ROLERESOURCENM 
        add constraint FKan33knkv6vxyubowoou51i24i 
        foreign key (`ROLE`) 
        references `ROLE` (`NAME`);

    alter table ROLERESOURCENM 
        add constraint FK8bg4vr1hdj04pnre1ilphwaya 
        foreign key (`RESOURCE`) 
        references `RESOURCE` (`NAME`);

    alter table ROLEROLENM 
        add constraint FK5f7bmwos053wnl5yi4ksxwmwo 
        foreign key (PARENTROLE) 
        references `ROLE` (`NAME`);

    alter table ROLEROLENM 
        add constraint FKko3y06p19y83p5oa48c1hemrp 
        foreign key (CHILDROLE) 
        references `ROLE` (`NAME`);

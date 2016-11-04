
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

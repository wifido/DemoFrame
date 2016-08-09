drop table if exists pcomp_title;
create table pcomp_title(
  id varchar(64) primary key comment '主键',
  name varchar(64) unique key comment '主题名称',
  introduction varchar(1024) comment '简介',
  avatar varchar(512) comment  '主题头像',
  block_image varchar(512) comment  '主题小背景图',
  gallery_image varchar(512) comment  '主题大背景图'
)engine = innodb comment '公共组件主题表';

drop table if exists pcomp_kind;
create table pcomp_kind (
  id varchar(64) primary key comment '主键',
  pcomp_title_id varchar(64) comment '对应公共组件主题主键',
  name varchar(64) comment '分类名称',
  unique key(pcomp_title_id,name)
)engine = innodb comment '公共组件类别表';

drop table if exists pcomp_software;
create table pcomp_software(
  id varchar(64) primary key comment '主键',
  pcomp_kind_id varchar(64) comment '对应公共组件类别主键',
  name varchar(64) comment '软件名称',
  avatar varchar(512) comment  '软件头像',
  introduction text comment '简介',
  unique key(pcomp_kind_id,name)
)engine = innodb comment '公共组件软件表';

drop table if exists pcomp_version;
create table pcomp_version(
  id varchar(64) primary key comment '主键',
  pcomp_software_id varchar(64) comment '对应公共组件软件主键',
  version_number varchar(32) comment '版本号',
  introduction text comment '简介',
  quick_start text comment '快速上手文档',
  unique key(pcomp_software_id,version_number)
)engine = innodb comment '公共组件版本表';

drop table if exists pcomp_version_platform_download;
create table pcomp_version_platform_download(
  id varchar(64) primary key comment '主键',
  pcomp_version_id varchar(64) comment '对应公共组件软件版本主键',
  platform varchar(64) comment '下载版本使用对应平台',
  download varchar(512) comment '版本下载地址'
)engine = innodb comment '公共组件版本下载与平台对应关系表';

drop table if exists pcomp_version_document_download;
create table pcomp_version_document_download(
  id varchar(64) primary key comment '主键',
  pcomp_version_id varchar(64) comment '对应公共组件软件版本主键',
  description varchar(64) comment '文档描述',
  download varchar(512) comment '文档下载地址'
)engine = innodb comment '公共组件文档下载与描述对应关系表';

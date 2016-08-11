drop table if exists pcomp_title;
create table pcomp_title(
  id varchar(64) primary key comment '主键',
  name varchar(64) unique key comment '主题名称',
  is_deleted tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除,0否1是',
  created_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  created_by int(11) NOT NULL DEFAULT -1 comment '创建人,默认为-1,-1是虚拟超管',
  modified_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  modified_by int(11) NOT NULL DEFAULT -1 comment '修改人,默认为-1,-1是虚拟超管',
  key (created_time),
  key (modified_time)
)engine = innodb comment '公共组件主题表';

drop table if exists pcomp_kind;
create table pcomp_kind (
  id varchar(64) primary key comment '主键',
  pcomp_title_id varchar(64) comment '对应公共组件主题主键',
  name varchar(64) comment '分类名称',
  banner_image varchar(512) comment '分类栏目图片',
  top_photo varchar(512) comment '分类栏目头像',
  introduction varchar(32) comment '一句话简介',
  unique key(pcomp_title_id,name),
  key(pcomp_title_id),
  key(name),
  is_deleted tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除,0否1是',
  created_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  created_by int(11) NOT NULL DEFAULT -1 comment '创建人,默认为-1,-1是虚拟超管',
  modified_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  modified_by int(11) NOT NULL DEFAULT -1 comment '修改人,默认为-1,-1是虚拟超管',
  key (created_time),
  key (modified_time)
)engine = innodb comment '公共组件类别表';

drop table if exists pcomp_software;
create table pcomp_software(
  id varchar(64) primary key comment '主键',
  pcomp_kind_id varchar(64) comment '对应公共组件类别主键',
  name varchar(64) comment '软件名称',
  avatar varchar(512) comment  '软件头像',
  introduction text comment '简介',
  unique key(pcomp_kind_id,name),
  key(pcomp_kind_id),
  key(name),
  is_deleted tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除,0否1是',
  created_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  created_by int(11) NOT NULL DEFAULT -1 comment '创建人,默认为-1,-1是虚拟超管',
  modified_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  modified_by int(11) NOT NULL DEFAULT -1 comment '修改人,默认为-1,-1是虚拟超管',
  key (created_time),
  key (modified_time)
)engine = innodb comment '公共组件软件表';

drop table if exists pcomp_version;
create table pcomp_version(
  id varchar(64) primary key comment '主键',
  pcomp_software_id varchar(64) comment '对应公共组件软件主键',
  version_number varchar(32) comment '版本号',
  introduction text comment '简介',
  quick_start text comment '快速上手文档',
  unique key(pcomp_software_id,version_number),
  key(pcomp_software_id),
  key(version_number),
  is_deleted tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除,0否1是',
  created_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  created_by int(11) NOT NULL DEFAULT -1 comment '创建人,默认为-1,-1是虚拟超管',
  modified_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  modified_by int(11) NOT NULL DEFAULT -1 comment '修改人,默认为-1,-1是虚拟超管',
  key (created_time),
  key (modified_time)
)engine = innodb comment '公共组件版本表';

drop table if exists pcomp_version_platform_download;
create table pcomp_version_platform_download(
  id varchar(64) primary key comment '主键',
  pcomp_version_id varchar(64) comment '对应公共组件软件版本主键',
  platform varchar(64) comment '下载版本使用对应平台',
  download varchar(512) comment '版本下载地址',
  key(pcomp_version_id)
)engine = innodb comment '公共组件版本下载与平台对应关系表';

drop table if exists pcomp_version_document_download;
create table pcomp_version_document_download(
  id varchar(64) primary key comment '主键',
  pcomp_version_id varchar(64) comment '对应公共组件软件版本主键',
  description varchar(64) comment '文档描述',
  download varchar(512) comment '文档下载地址',
  key(pcomp_version_id)
)engine = innodb comment '公共组件文档下载与描述对应关系表';


DROP TABLE IF EXISTS `permission_resource`;
CREATE TABLE `permission_resource` (
  `resource_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资源ID，自增主键',
  `parent_id` int(11) DEFAULT NULL COMMENT '父资源ID',
  `resource_type` varchar(32) DEFAULT NULL COMMENT '资源类型：菜单M，按钮B，子菜单S',
  `resource_url` varchar(128) DEFAULT NULL COMMENT '资源URL',
  `resource_name` varchar(32) DEFAULT NULL COMMENT '资源名称',
  `remark` varchar(128) DEFAULT NULL COMMENT '说明',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，0否1是',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='资源表';


DROP TABLE IF EXISTS `permission_role`;
CREATE TABLE `permission_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID，自增主键',
  `role_name` varchar(32) DEFAULT NULL COMMENT '角色名',
  `remark` varchar(128) DEFAULT NULL COMMENT '说明',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '是否逻辑删除：0否1是',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='角色表';


DROP TABLE IF EXISTS `permission_role_res_rel`;
CREATE TABLE `permission_role_res_rel` (
  `role_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modified_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user` varchar(45) DEFAULT NULL COMMENT '创建用户',
  `update_user` varchar(45) DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`role_id`,`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色资源对应表';



DROP TABLE IF EXISTS `permission_user_role_rel`;
CREATE TABLE `permission_user_role_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_no` varchar(32) NOT NULL COMMENT '工号',
  `role_id` varchar(32) DEFAULT NULL COMMENT '角色ID',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户角色对应关表，一个用户可以对应多个角色';


DROP TABLE IF EXISTS `permission_user`;
CREATE TABLE `permission_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id，自增主键',
  `user_no` varchar(16) NOT NULL COMMENT '用户工号',
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户姓名',
  `mobile` varchar(16) DEFAULT NULL COMMENT '用户手机号',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0否1是',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户表';


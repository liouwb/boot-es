-- es-boot测试数据表
CREATE TABLE `test_es`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NULL COMMENT '姓名',
  `age` int NULL COMMENT '年龄',
  `created_at` datetime NULL COMMENT '创建时间',
  `updated_at` datetime NULL COMMENT '修改时间',
  `is_deleted` tinyint NULL COMMENT '逻辑删除：0-正常 1-删除',
  PRIMARY KEY (`id`)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='boot-es测试数据表';

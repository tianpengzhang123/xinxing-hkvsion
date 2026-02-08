INSERT INTO `blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1847192919944974338', 1123598815738675201, 'blogDetail', '博客详细表', 'menu', '/demo/blogDetail', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1847192919944974339', '1847192919944974338', 'blogDetail_add', '新增', 'add', '/demo/blogDetail/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1847192919944974340', '1847192919944974338', 'blogDetail_edit', '修改', 'edit', '/demo/blogDetail/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1847192919944974341', '1847192919944974338', 'blogDetail_delete', '删除', 'delete', '/api/xinxing-demo/blogDetail/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1847192919944974342', '1847192919944974338', 'blogDetail_view', '查看', 'view', '/demo/blogDetail/view', 'file-text', 4, 2, 2, 1, NULL, 0);

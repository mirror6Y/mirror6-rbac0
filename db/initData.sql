##一级菜单
INSERT INTO system_menu ( id, parent_id, title, description, url, sort, gmt_create, gmt_modified ) VALUES ( 1, 0, '系统信息', '用户、角色、权限等系统信息', '/system', 1, '2021-03-22:03:45:00', '2021-03-22:03:45:00' );

##二级菜单
INSERT INTO system_menu ( id, parent_id, title, description, url, sort, gmt_create, gmt_modified ) VALUES ( 2, 1, '用户信息', '用户信息', '/system/user', 1, '2021-03-22:03:45:00', '2021-03-22:03:45:00' );
INSERT INTO system_menu ( id, parent_id, title, description, url, sort, gmt_create, gmt_modified ) VALUES ( 3, 1, '角色信息', '角色信息', '/system/role', 2, '2021-03-22:03:45:00', '2021-03-22:03:45:00' );
INSERT INTO system_menu ( id, parent_id, title, description, url, sort, gmt_create, gmt_modified ) VALUES ( 4, 1, '权限信息', '权限信息', '/system/auth', 3, '2021-03-22:03:45:00', '2021-03-22:03:45:00' );
INSERT INTO system_menu ( id, parent_id, title, description, url, sort, gmt_create, gmt_modified ) VALUES ( 5, 1, '菜单信息', '菜单信息', '/system/menu', 4, '2021-03-22:03:45:00', '2021-03-22:03:45:00' );


##一级权限 （菜单权限）
INSERT INTO system_authority ( id, parent_id, name, description, menu_id, sort, gmt_create, gmt_modified ) VALUES ( 1, 0, '系统信息菜单权限', '管理用户、角色、权限等菜单的权限', 1, 1, '2021-03-22:03:45:00', '2021-03-22:03:45:00' );

##二级权限 （菜单权限）
INSERT INTO system_authority ( id, parent_id, name, description, menu_id, sort, gmt_create, gmt_modified ) VALUES ( 2, 1, '用户信息菜单权限', '管理用户信息菜单的权限', 2, 1, '2021-03-22:03:45:00', '2021-03-22:03:45:00' );
INSERT INTO system_authority ( id, parent_id, name, description, menu_id, sort, gmt_create, gmt_modified ) VALUES ( 3, 1, '角色信息菜单权限', '管理角色信息菜单的权限', 3, 2, '2021-03-22:03:45:00', '2021-03-22:03:45:00' );
INSERT INTO system_authority ( id, parent_id, name, description, menu_id, sort, gmt_create, gmt_modified ) VALUES ( 4, 1, '权限信息菜单权限', '管理权限信息菜单的权限', 4, 3, '2021-03-22:03:45:00', '2021-03-22:03:45:00' );
INSERT INTO system_authority ( id, parent_id, name, description, menu_id, sort, gmt_create, gmt_modified ) VALUES ( 5, 1, '菜单信息菜单权限', '管理菜单信息菜单的权限', 5, 4, '2021-03-22:03:45:00', '2021-03-22:03:45:00' );

##三级权限（操作权限）
INSERT INTO system_authority ( id, parent_id, name, description, sort, gmt_create, gmt_modified ) VALUES ( 6, 2, '用户信息操作权限', '管理用户信息的操作权限', 1, '2021-03-22:03:45:00', '2021-03-22:03:45:00' );
INSERT INTO system_authority ( id, parent_id, name, description, sort, gmt_create, gmt_modified ) VALUES ( 7, 2, '用户信息查看权限', '管理用户信息的查看权限', 2, '2021-03-22:03:45:00', '2021-03-22:03:45:00' );

INSERT INTO system_authority ( id, parent_id, name, description, sort, gmt_create, gmt_modified ) VALUES ( 8, 3, '角色信息操作权限', '管理角色信息的操作权限', 3, '2021-03-22:03:45:00', '2021-03-22:03:45:00' );
INSERT INTO system_authority ( id, parent_id, name, description, sort, gmt_create, gmt_modified ) VALUES ( 9, 3, '角色信息查看权限', '管理角色信息的查看权限', 4, '2021-03-22:03:45:00', '2021-03-22:03:45:00' );

INSERT INTO system_authority ( id, parent_id, name, description, sort, gmt_create, gmt_modified ) VALUES ( 10, 4, '权限信息操作权限', '管理权限信息的操作权限', 5, '2021-03-22:03:45:00', '2021-03-22:03:45:00' );
INSERT INTO system_authority ( id, parent_id, name, description, sort, gmt_create, gmt_modified ) VALUES ( 11, 4, '权限信息查看权限', '管理权限信息的查看权限', 6, '2021-03-22:03:45:00', '2021-03-22:03:45:00' );

INSERT INTO system_authority ( id, parent_id, name, description, sort, gmt_create, gmt_modified ) VALUES ( 12, 5, '菜单信息操作权限', '管理菜单信息的操作权限', 7, '2021-03-22:03:45:00', '2021-03-22:03:45:00' );
INSERT INTO system_authority ( id, parent_id, name, description, sort, gmt_create, gmt_modified ) VALUES ( 13, 5, '菜单信息查看权限', '管理菜单信息的查看权限', 8, '2021-03-22:03:45:00', '2021-03-22:03:45:00' );


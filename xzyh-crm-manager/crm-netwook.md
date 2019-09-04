vo对象来接收从数据库查询到的结果然后返回到前端,接收也同样使用

查询，usert
106.13.141.29:3306/Crm
insert into sys_role (role_name, role_desc, create_time, 
      update_time, role_status, sys_user_id, 
      is_leader, pid)
    values ('dev','开发组员',NOW(),NOW(),1, null,0,3) 

查询全部或者模糊查询一个人
select * from sys_role where role_name like 'test' and

role_status = 1 order by create_time desc


role_status = 1 order by create_time desc

Java为每个原始类型提供了封装类，Integer是java为int提供的封装类。 int的默认值为0，而Integer的默认值为null，即Integer可以区分出未赋值和值为0的区别，int则无法表达出未赋值的情况

Integer为对象判断是否相等还是使用equals最靠谱

比较两个Integer的值是否相同，方法比较多:

1、推荐用equals()，这个还可以避免一些空指针问题的出现。

2、或者使用Integer.intValue();这样出来的就是int值，就可以直接比较了（可能会抛出空指针异常）；

在for循环中，continue的作用是从continue语句那一行结束，跳到下一次循环中，从循环头开始执行，洗面举例说明：

for(int i=0;i<10;i++){ int a = i+1; int b = a+i; if(b == 3){ continue;//此处的意思是，当b=3的时候，下面的代码不执行了 //也就是说当i=1的时候，下面的代码不执行了，从i=3开始运行 } b = a*i; } Long.valueOf(这里有参数)，是将参数转换成long的包装类——Long。 longValue()是Long类的一个方法，用来得到Long类中的数值。 前者是将基本数据类型转换成包装类 后者是将包装类中的数据拆箱成基本数据类型

alt + enter 万能快捷

//这个在菜单的添加操作中，生成它的最大的sort来使用的\\
这个sql是查询出来parentid相同的，sord的最大的值
select max(order_num) from sys_menu where parent_id=0
按钮的插入
 insert into menu_btn (btn_name, btn_class, btn_icon, 
      menu_id, menu_status, create_time
      )
    values ("查看", 'view','el-icon-news', 
      9, 1, NOW()
      )
https://lanhuapp.com/web/#/item/project/product?pid=fa7028d6-03fe-4395-8ed2-111d24d1e1b1&docId=fbc84e34-1811-43af-b97d-e73fb324e3fc&docType=axure&pageId=db498daaaf854d358ef82446cfedbb14&image_id=fbc84e34-1811-43af-b97d-e73fb324e3fc&parentId=2f0bc8cf-aec5-4c76-bdf8-8983ead4b4ce

于@RequestParam是用来处理 Content-Type 为 application/x-www-form-urlencoded 编码的内容的，所以在postman中，要选择body的类型为 x-www-form-urlencoded，这样在headers中就自动变为了 Content-Type : application/x-www-form-urlencoded 编码格式。如下图所示：

传入角色的id进行查询主的菜单和从的菜单，还有按钮
select m.* from menu_btn m, menu_role_ref rm
    where rm.sys_role_id = 1 and
		rm.subject_type= 2 and rm.type_id= 1
    and m.id = rm.subject_id

select m.* from sys_menu m, menu_role_ref rm
    where  
    rm.subject_type= 1 and rm.type_id= 1
    and m.id = rm.subject_id AND rm.sys_role_id in (SELECT role_id FROM `department_role` where department_id=1 and type=1)
传入的是部门的ID，查出这个部门主的菜单


这个是拿出的主的按钮
select m.* from menu_btn m, menu_role_ref rm
    where  
    rm.subject_type= 1 and rm.type_id= 1
    and m.id = rm.subject_id AND rm.sys_role_id in (SELECT role_id FROM `department_role` where department_id=1 and type=1)


传入的是部门从的ID，查出这个部门从的菜单department_id传入参数

select m.* from sys_menu m, menu_role_ref rm
    where  
    rm.subject_type= 1 and rm.type_id= 0
    and m.id = rm.subject_id AND rm.sys_role_id in (SELECT role_id FROM `department_role` where department_id=1 and type=0)


查出从部门的开始
select m.* from menu_btn m, menu_role_ref rm
    where  
    rm.subject_type= 1 and rm.type_id= 1
    and m.id = rm.subject_id AND rm.sys_role_id in (SELECT role_id FROM `department_role` where department_id=1 and type=0)
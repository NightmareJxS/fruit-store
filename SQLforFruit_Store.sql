create database Fruit_Store

use Fruit_Store

--drop table (might not drop all table, please refresh to make sure)
EXEC sp_msforeachtable "ALTER TABLE ? NOCHECK CONSTRAINT all"
drop table tblRoles;
drop table tblUsers;
drop table tblOrder;
drop table tblOrderDetail;
drop table tblCategory;
drop table tblProduct;

---------------------------------Tables-----------------------------------
--Note: pls create in order to link FK correctly (there might be some exception)
--tblRoles
create table tblRoles(
	roleID nvarchar(10) primary key,
	roleName nvarchar(50)
)

--tblUsers
create table tblUsers(
	userID nvarchar(50) primary key,--use nvarchar for using email as userID
	fullName nvarchar(50) not null,
	password nvarchar(50) not null,
	roleID nvarchar(10) foreign key references tblRoles(roleID),
	address nvarchar(50),
	birthdate date,
	phone nvarchar(50)
)

--tblOrder
create table tblOrder(
	orderID int primary key,
	orderDate date,
	total real,
	userID nvarchar(50) foreign key references tblUsers(userID)
)

--tblCategory
create table tblCategory(
	categoryID nvarchar(10) primary key,
	categoryName nvarchar(50)
)

--tblProduct
create table tblProduct(
	productID int primary key,
	productName nvarchar(50),
	image nvarchar(100),--image type subject to change (might be a url (xml) ? )***
	price real,
	quantity int,
	categoryID nvarchar(10) foreign key references tblCategory(categoryID),
	importDate date,
	usingDate date,
	status bit
)

--tblOrderDetail
create table tblOrderDetail(
	detailID int primary key,
	price real,
	quantity int,
	orderID int foreign key references tblOrder(orderID),
--Note : link to tblProduct after finish create tblCategory and tblProduct
	productID int foreign key references tblProduct(productID)
)

-----------------------------Insert Samples-------------------------------
--tblRoles
insert tblRoles values('AD','Admin')
insert tblRoles values('US','User')

--tblUsers
insert tblUsers values('abc@def.com','abc','cba','US',N'123 phạm ngũ lão p9 HCM','1994-03-19','0912345678')
insert tblUsers values('def@ghi.com','def','fde','US',N'654 lê quang định p3 Hà Nội','1991-12-03','0987654321')
insert tblUsers values('admin','Admin_server','admin','AD',null,null,null)
insert tblUsers values('727_osu','freedom_dive','shigetora','US',null,null,null)

--tblCategory
insert tblCategory values('RCQ',N'Rau-Củ-Quả')
insert tblCategory values('TPDG',N'Thực phẩm đóng gói')

--tblProduct
insert tblProduct values('8030823',N'Chanh không hạt Coop Select 250g','https://cooponline.vn/wp-content/uploads/2020/05/chanh-khong-hat-coop-select-250g.jpg','8900',23,'RCQ','2022-02-28','2022-03-04',1)
insert tblProduct values('8030841',N'Chanh không hạt Coop Select 250g','https://cooponline.vn/wp-content/uploads/2020/05/chanh-khong-hat-coop-select-250g.jpg','8900',70,'RCQ','2022-03-01','2022-03-08',1)
insert tblProduct values('8030842',N'Cải Xanh Đà Lạt Coop Select 450g','https://cooponline.vn/wp-content/uploads/2020/04/cai-xanh-da-lat-coop-select-450g.jpg','16900',0,'RCQ','2022-02-25','2022-03-01',0)
insert tblProduct values('8030843',N'Bí đỏ giống Mỹ – kg','https://cooponline.vn/wp-content/uploads/2020/07/bi-do-giong-my-kg.jpg','23500',44,'RCQ','2022-02-28','2022-03-04',1)
insert tblProduct values('8030527',N'Bánh thịt tươi chà bông karo PM.HK 6x26g','https://cooponline.vn/wp-content/uploads/2021/07/banh-thit-tuoi-cha-bong-karo-pm-hk-6x26g.jpg','38500',20,'TPDG','2022-02-10','2022-03-20',1)
insert tblProduct values('8030528',N'Trà sữa matcha trân châu Ciao','https://cooponline.vn/wp-content/uploads/2021/10/tra-sua-matcha-tran-chau-ciao.jpg','17500',9,'TPDG','2022-02-20','2022-03-15',1)
insert tblProduct values('8030529',N'Thạch đen Cao Bằng 480g','https://cooponline.vn/wp-content/uploads/2020/06/thach-den-cao-bang-480g.jpg','36500',17,'TPDG','2022-01-30','2022-03-07',1)


--tblOrder
insert tblOrder values(0,null,null,null)



--tblOrderDetail
insert tblOrderDetail values(0,0,0,null,null)

select Top 1 COUNT (*) OVER () AS ROW_COUNT from tblProduct where status=1

with subTable as(SELECT productID, productName, image, price, quantity, categoryID, importDate, usingDate, status, ROW_NUMBER() over(ORDER BY productID ASC) as row# FROM tblProduct where status=1) select productID, productName, image, price, quantity, categoryID, importDate, usingDate, status, row# from subTable where row# between 4 and 8


select * from tblProduct
select * from tblCategory
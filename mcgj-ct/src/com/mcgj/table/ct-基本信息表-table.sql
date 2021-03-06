--用户表
CREATETABLE	USER	用户表		ID	PRIMARY			
ID                  ID      			int(11)    		N	                              	                    		
ACCOUNT             账号  				VARCHAR(50) 	Y        
PASSWORD			密码					VARCHAR(100)	Y
USER_NAME			用户名称				VARCHAR(50)		Y
ADMIN				超级管理员(0否1是)		tinyint(1)		Y
CREATE_DATE			创建时间				datetime		Y
MODIFY_DATE			修改时间				datetime		Y
PHOTO				头像					VARCHAR(255)	Y
BACKGROUND			背景					VARCHAR(255)	Y
CARD_BANNER			卡片横幅				VARCHAR(255)	Y
AUTOGRAPH			贴吧签名				VARCHAR(100)	Y
STATE				状态(0不可用1可用)		tinyint(1)	Y
END

--用户收藏(贴子)表
CREATETABLE	USER_COLLECTION_CONVERSATION_CHILD		用户收藏表		ID	PRIMARY			
ID                  	ID      			int(11)    		N	                              	                    		
USER_ID             	用户id  				int(11) 		Y        
CONVERSATION_CHILD_ID	贴子id				int(11)			Y
create_date				创建时间(收藏时间)		datetime		Y
STATE					状态(0不可用1可用)		tinyint(1)		Y
END

--用户关注(贴吧)表
CREATETABLE	USER_FOLLOW_CONVERSATION	用户表		ID	PRIMARY			
ID                  	ID      			int(11)    		N	                              	                    		
USER_ID             	用户id  				int(11) 		Y        
CONVERSATION_ID			贴吧id				int(11)			Y
create_date				创建时间(收藏时间)		datetime		Y
STATE					状态(0不可用1可用)		tinyint(1)		Y
END

--贴吧(会话)表
CREATETABLE	CONVERSATION	贴吧表		ID	PRIMARY			
ID                  		ID      			int(11)    		N	                              	                    		
CREATE_DATE         		创建时间   			datatime	 	Y        
CREATE_USER_ID				创建人id				int(11)			Y
CONVERSATION_NAME			会话(贴吧)名称			VARCHAR(128) 	Y
CURRENT_MANAGE_USER_ID  	当前吧主id				int(11)			Y
CONVERSATION_TYPE			贴吧类型(1.动漫2.电影3.明星4.体育5.科技6.文化7.游戏...(未完待续)) 		int(11)		Y
PHOTO						头像封面				VARCHAR(512)	Y
FOLLOW_USER_NUMBER			关注用户数量			int(11)			Y
END


--用户关注贴吧(会话)子表(贴吧下的具体子帖子)
CREATETABLE	CONVERSATION_CHILD	用户贴吧子表		ID	PRIMARY			
ID                  		ID      			int(11)    		N	                              	                    		
CREATE_DATE         		创建时间   			datatime	 	Y        
CONVERSATION_ID				子帖子关联的贴吧			int(11)			Y
USER_ID						创建帖子用户id			int(11)			Y
TITLE						标题					VARCHAR(512)	Y
CONTENT						内容					text			Y
FLOW						流量(访问量)			int(11)			Y
STATE						状态(0不可用1可用)		tinyint(1) 		Y
END


--用户关注贴吧子表下的楼层表(该表还可以拓展，可以具体到楼层的回复-暂不扩展)
CREATETABLE	CONVERSATION_CHILD_CHILD	用户关注子表楼层表		ID	PRIMARY			
ID                  		ID      			int(11)    		N	                              	                    		
CREATE_DATE         		回复创建时间   			datatime	 	Y        
CONVERSATION_CHILD_ID		贴吧子表id				int(11)			Y
USER_ID 					楼层用户id				int(11)			Y
CONTENT						回复内容				text			Y
ISMANAGE					是否是楼主(0否1是)		tinyint(1) 		Y
STATE						状态(0不可用1可用)		tinyint(1) 		Y
is_look						楼主是否查看过(0没看1查看) tinyint(1) 		Y
END

--登录日志表
CREATETABLE	LOGIN_LOG	登录日志表		ID	PRIMARY			
ID                  ID      			int(11)    		N	                              	                    		
LOGIN_DATE          记录时间  				datatime	 	Y        
LOGIN_IP	        登录ip  				VARCHAR(512)	Y   
USER_ID				用户id				int(11)			Y
END

--字典表
CREATETABLE	DICT	数据字典表		ID	PRIMARY			
ID                  ID      			int(11)    		N	                              	                    		
CREATE_DATE         创建时间  				datatime	 	Y        
DICT_NAME	        字典名称  				VARCHAR(512)	Y   
CODE_VALUE			代码值				VARCHAR(512) 	Y
DICT_TYPE 			字典类型				VARCHAR(512)	Y
END

--用户关注用户表(用户直接相互关注关系表)
CREATETABLE	USER_FOLLOW_USER	数据字典表		ID	PRIMARY			
ID                  ID      			int(11)    		N	                              	                    		
CREATE_DATE         创建时间  				datatime	 	Y        
USER_ID				用户id(关注人)			int(11)			Y
FOLLOW_USER_ID		被关注人				int(11)			Y
END

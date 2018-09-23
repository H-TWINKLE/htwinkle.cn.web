package com.twinkle.entity;

import java.util.List;

public class Mblog {

	private String idstr;
	private boolean isLongText;
	private String created_at;
	private String mid;
	private int mblogtype;
	private String source;
	private int attitudes_count;
	private String bmiddle_pic;
	private int hide_flag;
	private int pending_approval_count;
	private String cardid;
	private String id;
	private String text;
	private boolean is_paid;
	private String picStatus;
	private int reposts_count;
	private int reward_exhibition_type;
	private boolean favorited;
	private int mblog_vip_type;
	private int content_auth;
	private VisibleBean visible;
	private EditConfigBean edit_config;
	private int weibo_position;
	private String thumbnail_pic;
	private String original_pic;
	private boolean can_edit;
	private int textLength;
	private int comments_count;
	private PageInfoBean page_info;
	private String bid;
	private UserBean user;
	private int more_info_type;
	private List<PicsBean> pics;

	public String getIdstr() {
		return idstr;
	}

	public void setIdstr(String idstr) {
		this.idstr = idstr;
	}

	public boolean isIsLongText() {
		return isLongText;
	}

	public void setIsLongText(boolean isLongText) {
		this.isLongText = isLongText;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public int getMblogtype() {
		return mblogtype;
	}

	public void setMblogtype(int mblogtype) {
		this.mblogtype = mblogtype;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getAttitudes_count() {
		return attitudes_count;
	}

	public void setAttitudes_count(int attitudes_count) {
		this.attitudes_count = attitudes_count;
	}

	public String getBmiddle_pic() {
		return bmiddle_pic;
	}

	public void setBmiddle_pic(String bmiddle_pic) {
		this.bmiddle_pic = bmiddle_pic;
	}

	public int getHide_flag() {
		return hide_flag;
	}

	public void setHide_flag(int hide_flag) {
		this.hide_flag = hide_flag;
	}

	public int getPending_approval_count() {
		return pending_approval_count;
	}

	public void setPending_approval_count(int pending_approval_count) {
		this.pending_approval_count = pending_approval_count;
	}

	public String getCardid() {
		return cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isIs_paid() {
		return is_paid;
	}

	public void setIs_paid(boolean is_paid) {
		this.is_paid = is_paid;
	}

	public String getPicStatus() {
		return picStatus;
	}

	public void setPicStatus(String picStatus) {
		this.picStatus = picStatus;
	}

	public int getReposts_count() {
		return reposts_count;
	}

	public void setReposts_count(int reposts_count) {
		this.reposts_count = reposts_count;
	}

	public int getReward_exhibition_type() {
		return reward_exhibition_type;
	}

	public void setReward_exhibition_type(int reward_exhibition_type) {
		this.reward_exhibition_type = reward_exhibition_type;
	}

	public boolean isFavorited() {
		return favorited;
	}

	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}

	public int getMblog_vip_type() {
		return mblog_vip_type;
	}

	public void setMblog_vip_type(int mblog_vip_type) {
		this.mblog_vip_type = mblog_vip_type;
	}

	public int getContent_auth() {
		return content_auth;
	}

	public void setContent_auth(int content_auth) {
		this.content_auth = content_auth;
	}

	public VisibleBean getVisible() {
		return visible;
	}

	public void setVisible(VisibleBean visible) {
		this.visible = visible;
	}

	public EditConfigBean getEdit_config() {
		return edit_config;
	}

	public void setEdit_config(EditConfigBean edit_config) {
		this.edit_config = edit_config;
	}

	public int getWeibo_position() {
		return weibo_position;
	}

	public void setWeibo_position(int weibo_position) {
		this.weibo_position = weibo_position;
	}

	public String getThumbnail_pic() {
		return thumbnail_pic;
	}

	public void setThumbnail_pic(String thumbnail_pic) {
		this.thumbnail_pic = thumbnail_pic;
	}

	public String getOriginal_pic() {
		return original_pic;
	}

	public void setOriginal_pic(String original_pic) {
		this.original_pic = original_pic;
	}

	public boolean isCan_edit() {
		return can_edit;
	}

	public void setCan_edit(boolean can_edit) {
		this.can_edit = can_edit;
	}

	public int getTextLength() {
		return textLength;
	}

	public void setTextLength(int textLength) {
		this.textLength = textLength;
	}

	public int getComments_count() {
		return comments_count;
	}

	public void setComments_count(int comments_count) {
		this.comments_count = comments_count;
	}

	public PageInfoBean getPage_info() {
		return page_info;
	}

	public void setPage_info(PageInfoBean page_info) {
		this.page_info = page_info;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public int getMore_info_type() {
		return more_info_type;
	}

	public void setMore_info_type(int more_info_type) {
		this.more_info_type = more_info_type;
	}

	public List<PicsBean> getPics() {
		return pics;
	}

	public void setPics(List<PicsBean> pics) {
		this.pics = pics;
	}

	public static class VisibleBean {
		/**
		 * list_id : 0 type : 0
		 */

		private int list_id;
		private int type;

		public int getList_id() {
			return list_id;
		}

		public void setList_id(int list_id) {
			this.list_id = list_id;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}
	}

	public static class EditConfigBean {
		/**
		 * edited : false
		 */

		private boolean edited;

		public boolean isEdited() {
			return edited;
		}

		public void setEdited(boolean edited) {
			this.edited = edited;
		}
	}

	public static class PageInfoBean {
		/**
		 * page_url :
		 * https://m.weibo.cn/p/searchall?containerid=231522type%3D1%26q%3D%23%E8%BD%B6%E4%BA%8B%E4%BA%92%E5%8A%A9%E5%B0%8F%E7%BB%84%23%26t%3D10&isnewpage=1&luicode=10000011&lfid=1076033700687004
		 * page_pic :
		 * {"url":"https://wx2.sinaimg.cn/thumb180/00001e64ly9fs1b7g8tfwj2050050wey.jpg"}
		 * page_title : #轶事互助小组# content2 : content1 : type : topic object_id :
		 * 1022:2315225ce1eb4f4caea36885fdca9b71b62cc7
		 */

		private String page_url;
		private PagePicBean page_pic;
		private String page_title;
		private String content2;
		private String content1;
		private String type;
		private String object_id;

		public String getPage_url() {
			return page_url;
		}

		public void setPage_url(String page_url) {
			this.page_url = page_url;
		}

		public PagePicBean getPage_pic() {
			return page_pic;
		}

		public void setPage_pic(PagePicBean page_pic) {
			this.page_pic = page_pic;
		}

		public String getPage_title() {
			return page_title;
		}

		public void setPage_title(String page_title) {
			this.page_title = page_title;
		}

		public String getContent2() {
			return content2;
		}

		public void setContent2(String content2) {
			this.content2 = content2;
		}

		public String getContent1() {
			return content1;
		}

		public void setContent1(String content1) {
			this.content1 = content1;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getObject_id() {
			return object_id;
		}

		public void setObject_id(String object_id) {
			this.object_id = object_id;
		}

		public static class PagePicBean {
			/**
			 * url : https://wx2.sinaimg.cn/thumb180/00001e64ly9fs1b7g8tfwj2050050wey.jpg
			 */

			private String url;

			public String getUrl() {
				return url;
			}

			public void setUrl(String url) {
				this.url = url;
			}
		}
	}

	public static class UserBean {
		
		private boolean follow_me;
		private boolean like_me;
		private String gender;
		private String profile_url;
		private boolean like;
		private int urank;
		private boolean verified;
		private String description;
		private String profile_image_url;
		private int verified_type;
		private int verified_type_ext;
		private String avatar_hd;
		private BadgeBean badge;
		private int mbtype;
		private String screen_name;
		private int statuses_count;
		private boolean close_blue_v;
		private int follow_count;
		private boolean following;
		private int followers_count;
		private String verified_reason;
		private long id;
		private int mbrank;
		private String cover_image_phone;

		public boolean isFollow_me() {
			return follow_me;
		}

		public void setFollow_me(boolean follow_me) {
			this.follow_me = follow_me;
		}

		public boolean isLike_me() {
			return like_me;
		}

		public void setLike_me(boolean like_me) {
			this.like_me = like_me;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public String getProfile_url() {
			return profile_url;
		}

		public void setProfile_url(String profile_url) {
			this.profile_url = profile_url;
		}

		public boolean isLike() {
			return like;
		}

		public void setLike(boolean like) {
			this.like = like;
		}

		public int getUrank() {
			return urank;
		}

		public void setUrank(int urank) {
			this.urank = urank;
		}

		public boolean isVerified() {
			return verified;
		}

		public void setVerified(boolean verified) {
			this.verified = verified;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getProfile_image_url() {
			return profile_image_url;
		}

		public void setProfile_image_url(String profile_image_url) {
			this.profile_image_url = profile_image_url;
		}

		public int getVerified_type() {
			return verified_type;
		}

		public void setVerified_type(int verified_type) {
			this.verified_type = verified_type;
		}

		public int getVerified_type_ext() {
			return verified_type_ext;
		}

		public void setVerified_type_ext(int verified_type_ext) {
			this.verified_type_ext = verified_type_ext;
		}

		public String getAvatar_hd() {
			return avatar_hd;
		}

		public void setAvatar_hd(String avatar_hd) {
			this.avatar_hd = avatar_hd;
		}

		public BadgeBean getBadge() {
			return badge;
		}

		public void setBadge(BadgeBean badge) {
			this.badge = badge;
		}

		public int getMbtype() {
			return mbtype;
		}

		public void setMbtype(int mbtype) {
			this.mbtype = mbtype;
		}

		public String getScreen_name() {
			return screen_name;
		}

		public void setScreen_name(String screen_name) {
			this.screen_name = screen_name;
		}

		public int getStatuses_count() {
			return statuses_count;
		}

		public void setStatuses_count(int statuses_count) {
			this.statuses_count = statuses_count;
		}

		public boolean isClose_blue_v() {
			return close_blue_v;
		}

		public void setClose_blue_v(boolean close_blue_v) {
			this.close_blue_v = close_blue_v;
		}

		public int getFollow_count() {
			return follow_count;
		}

		public void setFollow_count(int follow_count) {
			this.follow_count = follow_count;
		}

		public boolean isFollowing() {
			return following;
		}

		public void setFollowing(boolean following) {
			this.following = following;
		}

		public int getFollowers_count() {
			return followers_count;
		}

		public void setFollowers_count(int followers_count) {
			this.followers_count = followers_count;
		}

		public String getVerified_reason() {
			return verified_reason;
		}

		public void setVerified_reason(String verified_reason) {
			this.verified_reason = verified_reason;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public int getMbrank() {
			return mbrank;
		}

		public void setMbrank(int mbrank) {
			this.mbrank = mbrank;
		}

		public String getCover_image_phone() {
			return cover_image_phone;
		}

		public void setCover_image_phone(String cover_image_phone) {
			this.cover_image_phone = cover_image_phone;
		}

		public static class BadgeBean {
			/**
			 * zongyiji : 1 super_star_2018 : 1 dzwbqlx_2016 : 1 wenchuan_10th : 1
			 * user_name_certificate : 1 follow_whitelist_video : 1
			 */

			private int zongyiji;
			private int super_star_2018;
			private int dzwbqlx_2016;
			private int wenchuan_10th;
			private int user_name_certificate;
			private int follow_whitelist_video;

			public int getZongyiji() {
				return zongyiji;
			}

			public void setZongyiji(int zongyiji) {
				this.zongyiji = zongyiji;
			}

			public int getSuper_star_2018() {
				return super_star_2018;
			}

			public void setSuper_star_2018(int super_star_2018) {
				this.super_star_2018 = super_star_2018;
			}

			public int getDzwbqlx_2016() {
				return dzwbqlx_2016;
			}

			public void setDzwbqlx_2016(int dzwbqlx_2016) {
				this.dzwbqlx_2016 = dzwbqlx_2016;
			}

			public int getWenchuan_10th() {
				return wenchuan_10th;
			}

			public void setWenchuan_10th(int wenchuan_10th) {
				this.wenchuan_10th = wenchuan_10th;
			}

			public int getUser_name_certificate() {
				return user_name_certificate;
			}

			public void setUser_name_certificate(int user_name_certificate) {
				this.user_name_certificate = user_name_certificate;
			}

			public int getFollow_whitelist_video() {
				return follow_whitelist_video;
			}

			public void setFollow_whitelist_video(int follow_whitelist_video) {
				this.follow_whitelist_video = follow_whitelist_video;
			}
		}
	}

	public static class PicsBean {
		/**
		 * geo : {"croped":false,"width":360,"height":640} size : orj360 large :
		 * {"geo":{"croped":false,"width":"1080","height":"1920"},"size":"large","url":"https://wx1.sinaimg.cn/large/dc94009cgy1fvf3x9xx4oj20u01hcaep.jpg"}
		 * pid : dc94009cgy1fvf3x9xx4oj20u01hcaep url :
		 * https://wx1.sinaimg.cn/orj360/dc94009cgy1fvf3x9xx4oj20u01hcaep.jpg
		 */

		private GeoBean geo;
		private String size;
		private LargeBean large;
		private String pid;
		private String url;

		public GeoBean getGeo() {
			return geo;
		}

		public void setGeo(GeoBean geo) {
			this.geo = geo;
		}

		public String getSize() {
			return size;
		}

		public void setSize(String size) {
			this.size = size;
		}

		public LargeBean getLarge() {
			return large;
		}

		public void setLarge(LargeBean large) {
			this.large = large;
		}

		public String getPid() {
			return pid;
		}

		public void setPid(String pid) {
			this.pid = pid;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public static class GeoBean {
			/**
			 * croped : false width : 360 height : 640
			 */

			private boolean croped;
			private int width;
			private int height;

			public boolean isCroped() {
				return croped;
			}

			public void setCroped(boolean croped) {
				this.croped = croped;
			}

			public int getWidth() {
				return width;
			}

			public void setWidth(int width) {
				this.width = width;
			}

			public int getHeight() {
				return height;
			}

			public void setHeight(int height) {
				this.height = height;
			}
		}

		public static class LargeBean {
			/**
			 * geo : {"croped":false,"width":"1080","height":"1920"} size : large url :
			 * https://wx1.sinaimg.cn/large/dc94009cgy1fvf3x9xx4oj20u01hcaep.jpg
			 */

			private GeoBeanX geo;
			private String size;
			private String url;

			public GeoBeanX getGeo() {
				return geo;
			}

			public void setGeo(GeoBeanX geo) {
				this.geo = geo;
			}

			public String getSize() {
				return size;
			}

			public void setSize(String size) {
				this.size = size;
			}

			public String getUrl() {
				return url;
			}

			public void setUrl(String url) {
				this.url = url;
			}

			public static class GeoBeanX {
				/**
				 * croped : false width : 1080 height : 1920
				 */

				private boolean croped;
				private String width;
				private String height;

				public boolean isCroped() {
					return croped;
				}

				public void setCroped(boolean croped) {
					this.croped = croped;
				}

				public String getWidth() {
					return width;
				}

				public void setWidth(String width) {
					this.width = width;
				}

				public String getHeight() {
					return height;
				}

				public void setHeight(String height) {
					this.height = height;
				}
			}
		}
	}
}
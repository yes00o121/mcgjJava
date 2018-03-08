package com.yc;
/**
 * 下载视频
 * @author ad
 *
 */
public class Video {
	
	private String url;//下载路径
	
	private String filePath;//保存路径
	
	private String hostpath = "";//根目录
	
	public Video(){
		
	}
	public Video(String url,String path){
		this.url = url;
		this.filePath = path;
		this.hostpath = Util.getAbsPath(url);//获取根目录
		this.downLoadVideo();
	}
	/**
	 * 下载视频
	 */
	private void downLoadVideo(){
//		Util.getHTML(url)
	}
	public static void main(String[] args) {
//		String str = Util.getHTML("http://www.acfun.cn/v/ac4210836");
//		System.out.println(str);
		
	}
}

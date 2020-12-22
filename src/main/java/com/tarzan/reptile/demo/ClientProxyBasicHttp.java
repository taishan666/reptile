package com.tarzan.reptile.demo;

import cn.hutool.http.HttpUtil;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 
 * @author tarzan Liu
 *
 */
public class ClientProxyBasicHttp {

	//获取ip代理的api
	public static String IpProxyAPI="http://webapi.http.zhimacangku.com/getip?num=20&type=1&pro=&city=0&yys=0&port=1&pack=129960&ts=0&ys=0&cs=0&lb=1&sb=0&pb=4&mr=1&regions=";

	// 目标地址
	public static String targetUrl = "https://blog.csdn.net/weixin_40986713";

	// 代理用户验证
	String proxyUser = "zhima";
	String proxyPwd = "zhima";


	public static void main(String args[]) {
		ClientProxyBasicHttp clientProxy=new ClientProxyBasicHttp();
		List<IpDTO> list= clientProxy.getIpList();
		list.forEach(e->{
			clientProxy.proxyRequest(e.getIp(),e.getPort(),targetUrl);
		});
	}


	/**
	 * 代理ip列表
	 *
	 * @return
	 * @throws IOException
	 */
	public  List<IpDTO> getIpList(){
		List<IpDTO> list= Lists.newArrayList();
		String body= HttpUtil.createGet(IpProxyAPI).execute().body();
		String[] ipList=body.split("\n");
		for (String e : ipList) {
			String ip=e.split(":")[0].replace("\r","");
			String portStr=e.split(":")[1].replace("\r","");
			int port=Integer.valueOf(portStr);
			list.add(new IpDTO(ip,port));
		}
		return  list;
	}


	/**
	 * 代理请求
	 *
	 * @param proxyHost&proxyPort&targetUrl
	 * @return
	 * @throws IOException
	 */
	public  String proxyRequest(String proxyHost,int proxyPort,String targetUrl){

		// http代理: Proxy.Type.HTTP, socks代理: Proxy.Type.SOCKS
		Proxy.Type proxyType = Proxy.Type.HTTP;


		try {
			// 设置验证
			Authenticator.setDefault(new ProxyAuthenticator(proxyUser, proxyPwd));

			// 创建代理服务器
			InetSocketAddress addr = new InetSocketAddress(proxyHost, proxyPort);
			Proxy proxy = new Proxy(proxyType, addr);
			// 访问目标网页
			URL url = new URL(targetUrl);
			URLConnection conn = url.openConnection(proxy);
			// 读取返回数据
			InputStream in = conn.getInputStream();
			// 将返回数据转换成字符串
			System.out.println(IO2String(in));

		} catch (Exception e) {
			e.printStackTrace();
		}
      return null;
	}

	/**
	 * 将输入流转换成字符串
	 * 
	 * @param inStream
	 * @return
	 * @throws IOException
	 */
	public static String IO2String(InputStream inStream) throws IOException {
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = inStream.read(buffer)) != -1) {
			result.write(buffer, 0, len);
		}
		String str = result.toString(StandardCharsets.UTF_8.name());
		return str;
	}
	
	static class ProxyAuthenticator extends Authenticator {
		private String authUser, authPwd;
		
		public ProxyAuthenticator(String authUser, String authPwd) {
			this.authUser = authUser;
			this.authPwd = authPwd;
		}
		
        public PasswordAuthentication getPasswordAuthentication() {
            return (new PasswordAuthentication(authUser, authPwd.toCharArray()));
        }
    }


	@Data
	class IpDTO{
		private   String ip;
		private  int port;
		public IpDTO(String ip, int port) {
			this.ip = ip;
			this.port = port;
		}
	}
}

package com.yx.firstGame;


import com.yx.firstGame.listener.NettyServerListener;

public class FirstGameApplication {

	public static void main(String[] args) {

		new NettyServerListener().start();
	}


}

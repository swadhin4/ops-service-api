package com.ops.api.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class ReadRemoteServerFile {

	public static void main(String[] args) {

		JSch jsch = new JSch();
		Session session = null;
		try {
			session = jsch.getSession("lg338738", "10.42.6.97", 22);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword("anu__77JENA");
			session.connect();
			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp sftpChannel = (ChannelSftp) channel;
			sftpChannel.cd("/data/ingest/geagp_move/support/");
            Vector filelist = sftpChannel.ls("/data/ingest/geagp_move/support/");
            for (int i = 0; i < filelist.size(); i++) {
                System.out.println(filelist.get(i).toString());
            }
			InputStream stream = sftpChannel.get("/data/ingest/geagp_move/support/product_master.ddl");
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(stream));
				String line;
				while ((line = br.readLine()) != null) {
					System.out.println(line);
				}
			} catch (IOException io) {
				System.out.println("Exception occurred during reading file from SFTP server due to " + io.getMessage());
				io.getMessage();

			} catch (Exception e) {
				System.out.println("Exception occurred during reading file from SFTP server due to " + e.getMessage());
				e.getMessage();
			}

			sftpChannel.exit();
			session.disconnect();
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		}

	}
}

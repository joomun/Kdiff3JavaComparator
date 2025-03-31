package ftp;

import com.jcraft.jsch.*;

import java.io.*;
import java.util.Properties;

public class FTPDownloader {

    private static final int BUFFER_SIZE = 4096;

    public void downloadFile(String host, String username, String password, String remoteFile, String localFile) throws JSchException, SftpException, IOException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");

        session.connect();
        
        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();

        try (InputStream inputStream = channelSftp.get(remoteFile);
             OutputStream outputStream = new FileOutputStream(localFile)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int readCount;
            while ((readCount = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, readCount);
            }
        } finally {
            channelSftp.exit();
            session.disconnect();
        }
    }
}

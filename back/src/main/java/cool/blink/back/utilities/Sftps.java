package cool.blink.back.utilities;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Sftps {

    public static final synchronized void copy(final File from, final String to, final String hostname, final String username, final String password, final Integer port) throws FileNotFoundException, JSchException, SftpException {
        JSch jsch = new JSch();
        Session session;
        session = jsch.getSession(username, hostname, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        ChannelSftp channel;
        channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();
        channel.cd(to);
        channel.put(new FileInputStream(from), from.getName());
        channel.disconnect();
        session.disconnect();
    }

}

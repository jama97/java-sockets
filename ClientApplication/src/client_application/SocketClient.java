package client_application;

import java.net.*;
import java.io.*;

public class SocketClient extends javax.swing.JFrame{
    
/* Declaration of global variables needed: ServerSocket, Port, Printwriter and BufferedReader.
   --------------------------------------------------------------------------------------- */    
    String username, address = "localhost";
    Socket client_socket;
    final int PORT = 2222;
    Boolean connected = false;
    BufferedReader reader;
    PrintWriter writer;
    
/* Initialization of buttons anf framework from the Design frame
--------------------------------------------------------------------------------------- */
    public SocketClient(){
        initComponents();
        tf_port.setText(PORT+"");
    }
    
/* Implementation on how the client reacts, displays as well as replies to the messages from the Server as they communicate.
   --------------------------------------------------------------------------------------- */
    public class ClientProtocol implements Runnable{
        @Override
        public void run() {
            String[] data;
            String stream, done = "Done", chat = "Chat", connect="Connect";
            try {
                while ((stream = reader.readLine()) != null) {
                    data = stream.split(":");
                    if (data[0].equals(chat)) {
                       ta_chat.append("Server: "+data[1] + "\n");
                       ta_chat.setCaretPosition(ta_chat.getDocument().getLength());
                    }else if (data[0].equals(done)){
                       ta_chat.append("Server: "+data[1] + "\n");
                       ta_chat.removeAll();
                       connected= false;
                     }else if (data[0].equals(connect)){
                       ta_chat.append("Server: "+data[1] + "\n");
                       tf_personal.setText(data[2]);
                     }
                }
           }catch(IOException ex) { }
        }
    }       
  /* Defining the function of the thread initialization and starting
   --------------------------------------------------------------------------------------- */   
     public void ListenThread() 
    {
         Thread IncomingReader = new Thread(new ClientProtocol());
         IncomingReader.start();
    }
     
       /* Defining the function of connecting the client to the server
   --------------------------------------------------------------------------------------- */ 
    public void connectClient(){
        if (connected == false){
                username = tf_username.getText();
                if("".equals(username)){
                    ta_chat.append("Connection can not been done without username. \n");
                }else{
                tf_username.setEditable(false);
                try{

                    client_socket = new Socket(address, PORT);
                    InputStreamReader streamreader = new InputStreamReader(client_socket.getInputStream());
                    reader = new BufferedReader(streamreader);
                    writer = new PrintWriter(client_socket.getOutputStream());
                    writer.println(username + ":has connected.:Connect");
                    writer.flush();
                    connected = true;
                    ta_chat.append("Successful! Connection has been established. \n");
                }catch (IOException ex){
                    ta_chat.append("Trouble Connecting! Please Try Again...Server Might not be On. \n");
                    tf_username.setEditable(true);
                }
                ListenThread();
            }
        }else if(connected == true){
            ta_chat.append("You are already connected. \n");
        }
    }
     
     /* Defining the function of sending the message to the server
   --------------------------------------------------------------------------------------- */ 
    public void sendMessage(){
        if (connected == true){
            String nothing = "";
            if ((tf_chat.getText()).equals(nothing)) {
                tf_chat.setText("");
                tf_chat.requestFocus();
            }else{
                try{
                    writer.println(username + ":" + tf_chat.getText() + ":" + "Reply");
                    ta_chat.append("Me: "+tf_chat.getText() + "\n");
                    writer.flush(); // flushes the buffer
                } catch (Exception ex) {
                    ta_chat.append("Message was not sent. \n");
                }
                tf_chat.setText("");
                tf_chat.requestFocus();
            }

            tf_chat.setText("");
            tf_chat.requestFocus();
        }else if(connected == false){
            ta_chat.append("Trouble Connecting!...Server Might not be On. \n");
        }
    }
    
  /* Defining the function of disconnecting client from server
   --------------------------------------------------------------------------------------- */ 
    public void sendDisconnect(){
        String bye = ("Disconnect:");
        try{
            writer.println(bye); 
            writer.flush(); 
            DisconnectClient();
        } catch (Exception e){
            ta_chat.append("Could not send Disconnect message.\n");
        }
    }
    
   /* Defining the function of the pre-disconnecting procedure client from server
   --------------------------------------------------------------------------------------- */   
    public void DisconnectClient(){
        try{
            ta_chat.append("Disconnected.\n");
            client_socket.close();
        } catch(IOException ex) {
            ta_chat.append("Failed to disconnect. \n");
        }
        connected = false;
        tf_username.setEditable(true);

    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lb_address = new javax.swing.JLabel();
        tf_address = new javax.swing.JTextField();
        lb_port = new javax.swing.JLabel();
        tf_personal = new javax.swing.JTextField();
        lb_username = new javax.swing.JLabel();
        tf_username = new javax.swing.JTextField();
        b_connect = new javax.swing.JButton();
        b_disconnect = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ta_chat = new javax.swing.JTextArea();
        tf_chat = new javax.swing.JTextField();
        b_send = new javax.swing.JButton();
        lb_username1 = new javax.swing.JLabel();
        tf_port = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Client Application");
        setLocation(new java.awt.Point(0, 0));
        setMaximizedBounds(new java.awt.Rectangle(50, 50, 0, 0));
        setName("client"); // NOI18N
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(37, 62, 74));
        jPanel1.setPreferredSize(new java.awt.Dimension(560, 430));

        lb_address.setFont(new java.awt.Font("Montserrat Medium", 0, 13)); // NOI18N
        lb_address.setForeground(new java.awt.Color(255, 255, 255));
        lb_address.setText("Address : ");

        tf_address.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        tf_address.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tf_address.setText("localhost");
        tf_address.setDisabledTextColor(new java.awt.Color(0, 72, 110));
        tf_address.setEnabled(false);
        tf_address.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_addressActionPerformed(evt);
            }
        });

        lb_port.setFont(new java.awt.Font("Montserrat Medium", 0, 12)); // NOI18N
        lb_port.setForeground(new java.awt.Color(255, 255, 255));
        lb_port.setText("Port :");

        tf_personal.setEditable(false);
        tf_personal.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        tf_personal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tf_personal.setText("0000");
        tf_personal.setToolTipText("");
        tf_personal.setDisabledTextColor(new java.awt.Color(0, 72, 110));
        tf_personal.setEnabled(false);
        tf_personal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_personalActionPerformed(evt);
            }
        });

        lb_username.setFont(new java.awt.Font("Montserrat Medium", 0, 13)); // NOI18N
        lb_username.setForeground(new java.awt.Color(255, 255, 255));
        lb_username.setText("Enter Username :");

        tf_username.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        tf_username.setForeground(new java.awt.Color(0, 72, 110));
        tf_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_usernameActionPerformed(evt);
            }
        });

        b_connect.setBackground(new java.awt.Color(83, 187, 97));
        b_connect.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        b_connect.setForeground(new java.awt.Color(255, 255, 255));
        b_connect.setText("Connect");
        b_connect.setBorderPainted(false);
        b_connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_connectActionPerformed(evt);
            }
        });

        b_disconnect.setBackground(new java.awt.Color(153, 0, 0));
        b_disconnect.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        b_disconnect.setForeground(new java.awt.Color(255, 255, 255));
        b_disconnect.setText("Disconnect");
        b_disconnect.setBorderPainted(false);
        b_disconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_disconnectActionPerformed(evt);
            }
        });

        ta_chat.setEditable(false);
        ta_chat.setColumns(20);
        ta_chat.setFont(new java.awt.Font("Montserrat Light", 0, 14)); // NOI18N
        ta_chat.setRows(5);
        ta_chat.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(ta_chat);

        b_send.setBackground(new java.awt.Color(0, 102, 153));
        b_send.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        b_send.setForeground(new java.awt.Color(255, 255, 255));
        b_send.setText("Send Message");
        b_send.setBorderPainted(false);
        b_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_sendActionPerformed(evt);
            }
        });

        lb_username1.setFont(new java.awt.Font("Montserrat Medium", 0, 13)); // NOI18N
        lb_username1.setForeground(new java.awt.Color(255, 255, 255));
        lb_username1.setText("Your Personal Code:");

        tf_port.setEditable(false);
        tf_port.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        tf_port.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tf_port.setText("8084");
        tf_port.setToolTipText("");
        tf_port.setDisabledTextColor(new java.awt.Color(0, 72, 110));
        tf_port.setEnabled(false);
        tf_port.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_portActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lb_address, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_address, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lb_username1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lb_username, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_username, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(lb_port)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tf_personal, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_port, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(b_disconnect, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b_connect, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(tf_chat, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(b_send, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(18, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tf_address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lb_address)
                        .addComponent(lb_username1)
                        .addComponent(tf_personal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(b_connect, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tf_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lb_username, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lb_port)
                        .addComponent(tf_port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(b_disconnect, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(387, 387, 387))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap(98, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(tf_chat, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                        .addComponent(b_send, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void b_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_sendActionPerformed
       sendMessage();
    }//GEN-LAST:event_b_sendActionPerformed

    private void b_disconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_disconnectActionPerformed
        sendDisconnect();
    }//GEN-LAST:event_b_disconnectActionPerformed

    private void b_connectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_connectActionPerformed
        connectClient();
    }//GEN-LAST:event_b_connectActionPerformed

    private void tf_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_usernameActionPerformed

    }//GEN-LAST:event_tf_usernameActionPerformed

    private void tf_personalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_personalActionPerformed

    }//GEN-LAST:event_tf_personalActionPerformed

    private void tf_addressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_addressActionPerformed

    }//GEN-LAST:event_tf_addressActionPerformed

    private void tf_portActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_portActionPerformed
       
        
    }//GEN-LAST:event_tf_portActionPerformed
        //--------------------------//
    
    public static void main(String args[])  {
        
        java.awt.EventQueue.invokeLater(() -> {
            new SocketClient().setVisible(true);
        });
       
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_connect;
    private javax.swing.JButton b_disconnect;
    private javax.swing.JButton b_send;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_address;
    private javax.swing.JLabel lb_port;
    private javax.swing.JLabel lb_username;
    private javax.swing.JLabel lb_username1;
    private javax.swing.JTextArea ta_chat;
    private javax.swing.JTextField tf_address;
    private javax.swing.JTextField tf_chat;
    private javax.swing.JTextField tf_personal;
    private javax.swing.JTextField tf_port;
    private javax.swing.JTextField tf_username;
    // End of variables declaration//GEN-END:variables
}

package server_application;

import java.io.*;
import java.net.*;

public class SocketServer extends javax.swing.JFrame {
    
/* Declaration of global variables needed: ServerSocket, Port, Printwriter and BufferedReader.
   --------------------------------------------------------------------------------------- */
    ServerSocket server_socket;         
    final int PORT = 2222;
    Socket clientSocket;
    PrintWriter writer;
    BufferedReader reader;
    
    
/* Initialization of the JFrame in the main function.
   --------------------------------------------------------------------------------------- */    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new SocketServer().setVisible(true);
        });    
    }
    
/* Initialization of buttons anf framework from the Design frame
--------------------------------------------------------------------------------------- */
    public SocketServer(){
        initComponents();
    }
    
    
   /* Function to Start the Server by Initializing the declared variables
   --------------------------------------------------------------------------------------- */
    public class ServerStart implements Runnable {

     @Override
        public void run() {
            try {
              server_socket = new ServerSocket(PORT);       // Setting the port for the Server communicaton
              ta_chat.append("Starting Server.... Socket Server Initialized.\n");       // Display Message
              
              // Creating a thread to listen in for new connection request and accepting the request.
                while (true) {
                    clientSocket = server_socket.accept();
                    writer = new PrintWriter(clientSocket.getOutputStream());
                    System.out.println(writer);
                    Thread listener = new Thread(new ServerProtocol(clientSocket, writer));
                    listener.start();
                    ta_chat.append("A Connection has been established \n");  
                }
            }catch (IOException ex){
               ta_chat.append("Error making a connection. \n");
            }
        }
    }
  /* Implementation on how the server reacts as well as replies to the messages from the Client as they communicate.
   --------------------------------------------------------------------------------------- */
    public class ServerProtocol implements Runnable {
       BufferedReader read;
       Socket sock;
       PrintWriter client;
       //Defining a parameterized function of the server protocol
       public ServerProtocol(Socket clientSocket, PrintWriter user) {
            client = user;
            try{
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                read = new BufferedReader(isReader);
            }catch (IOException ex){
                ta_chat.append("Unexpected error... \n");
            }
       }
       @Override
       public void run() {
            String message, connect = "Connect", disconnect = "Disconnect", reply = "Reply" ;
            String[] data;
            String[] personal;
            int pointer = 0;
            // checking for incoming message, displaying the as well as replying to the message using the Instructclient function
            try {
                while ((message = read.readLine()) != null){
                    //Defining the personal code from the writer ID and sending it to the client
                    data = message.split(":");
                    if (data[2].equals(connect)){
                    String personal_data = writer+"";
                    personal = personal_data.split("@");
                    
                    ta_chat.append(data[0] + " has been connected with personal code " + personal[1]+ "\n");
                    instructClient("Connect:Hello "+ data[0]+"... Your personal code is "+ personal[1]+":"+ personal[1]+ "\n");
                    
                    // Definition of the predefined Tasks of the Server
                    ta_chat.append("Requesting Admission Number: \n");
                    instructClient("Chat:Please send you Admission Number."+ "\n");
                    pointer=1;
                        
                    }else if (data[2].equals(disconnect)){
                         ta_chat.append(data[0] + " has been disconnected." + "\n");
                         
                    }else if (data[2].equals(reply)){
                        ta_chat.append("Message Received from " + data[0] +": "+ data[1] + "\n");
                        switch (pointer) {
                             case 0:
                                 ta_chat.append("Connection Terminated: \n");
                                 instructClient("Done:End of conversation.. Disconnecting:"+ "\n");
                                 break;
                             case 1:
                                 ta_chat.append("Requesting for Full Name: \n");
                                 instructClient("Chat:Please send your Full Name (First Name, Surname):"+ "\n");
                                 pointer=2;
                                 break;
                             case 2:
                                 ta_chat.append("Requesting for School Details: \n");
                                 instructClient("Chat:Please send your Student Faculty,Degree and Course:"+ "\n");
                                 pointer=3;
                                 break;
                             case 3:
                                 ta_chat.append("Requesting for Personal Code: \n");
                                 instructClient("Chat:Please send your Personal Code:"+ "\n");
                                 pointer=4;
                                 break;
                             case 4:
                                 ta_chat.append("Requesting for one Instruction: \n");
                                 instructClient("Chat:Please send all the above in one instruction separated by a ',':"+ "\n");
                                 pointer=5;
                                 break;
                             case 5:
                                 ta_chat.append("Ending Communication: \n");
                                 instructClient("Chat:Success! Thank for your cooperation. Goodbye!"+ "\n");
                                 pointer=0;
                                 break;
                             default:
                                 break;
                        }
                    }else{
                                ta_chat.append("No Conditions were met. \n");
                    }
                } 
             } 
            catch (IOException ex){
                ta_chat.append("Lost the connection to the Client. \n");
            } 
	} 
    }

  /* Defining a function used by the Server Protocol to send a reply to the client
   --------------------------------------------------------------------------------------- */
    public void instructClient(String message) {
        try{
            writer =  new PrintWriter(clientSocket.getOutputStream());
            writer.println(message);
            //ta_chat.append("Sending: " + message + "\n");
            writer.flush();
            ta_chat.setCaretPosition(ta_chat.getDocument().getLength());
        } 
        catch (IOException ex){
            ta_chat.append("Error instructing client. \n");
        }
    }
    
    /* Defining a function used by the stop button to disconnect from the client
   --------------------------------------------------------------------------------------- */
    public void disconnectClient() {
       try {
           instructClient("Done:End of conversation.. Disconnecting:"+ "\n");
            Thread.sleep(2000); 
        } 
        catch(InterruptedException ex ) {Thread.currentThread().interrupt();}
     }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ta_chat = new javax.swing.JTextArea();
        b_start = new javax.swing.JButton();
        b_end = new javax.swing.JButton();
        b_clear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server Application");
        setBackground(new java.awt.Color(51, 37, 78));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximizedBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setName("server"); // NOI18N
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        jPanel1.setBackground(new java.awt.Color(51, 37, 62));

        jScrollPane1.setBackground(new java.awt.Color(51, 37, 78));

        ta_chat.setEditable(false);
        ta_chat.setColumns(20);
        ta_chat.setFont(new java.awt.Font("Montserrat Light", 0, 14)); // NOI18N
        ta_chat.setRows(5);
        jScrollPane1.setViewportView(ta_chat);

        b_start.setBackground(new java.awt.Color(83, 187, 97));
        b_start.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        b_start.setForeground(new java.awt.Color(255, 255, 255));
        b_start.setText("Start");
        b_start.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        b_start.setBorderPainted(false);
        b_start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_startActionPerformed(evt);
            }
        });

        b_end.setBackground(new java.awt.Color(153, 0, 0));
        b_end.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        b_end.setForeground(new java.awt.Color(255, 255, 255));
        b_end.setText("Stop");
        b_end.setBorderPainted(false);
        b_end.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_endActionPerformed(evt);
            }
        });

        b_clear.setBackground(new java.awt.Color(0, 102, 153));
        b_clear.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        b_clear.setForeground(new java.awt.Color(255, 255, 255));
        b_clear.setText("Clear");
        b_clear.setBorderPainted(false);
        b_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_clearActionPerformed(evt);
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
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(b_start, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(b_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(b_end, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b_end, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b_start, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void b_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_clearActionPerformed
        ta_chat.setText("");
    }//GEN-LAST:event_b_clearActionPerformed

    private void b_endActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_endActionPerformed
       instructClient("Done:Server Disconnected! All client connection will be terminated.\n");
       ta_chat.setText("");
       ta_chat.append("All client connection has been terminated... \n");
       disconnectClient();
    }//GEN-LAST:event_b_endActionPerformed

    private void b_startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_startActionPerformed
        Thread starter = new Thread(new ServerStart());
        starter.start();
    }//GEN-LAST:event_b_startActionPerformed

   
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_clear;
    private javax.swing.JButton b_end;
    private javax.swing.JButton b_start;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea ta_chat;
    // End of variables declaration//GEN-END:variables
}

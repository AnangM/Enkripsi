/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enigma;

/**
 *
 * @author anangmb
 */
import java.awt.datatransfer.*;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    public Main() {
        //mengubah tema tampilan
        try{
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        }catch (Exception e){
            e.printStackTrace();
        }
        initComponents();
        //membuat agar textArea output tidak dapat diubah oleh pengguna
       output.setEditable(false);
       //membuat agar kursor langsung berada pada textArea masukan
       inputField.requestFocus();
    }
    
    Helper helper = new Helper();
    
    private void encrypt(){
        //mengambil setingan "kunci" enkripsi
        /*
        * Mengapa ditambah satu? karena untuk mendapatkan value dari dropdown
        * tersebut, sehingga tidak perlu melakukan perubahan tipe data dari 
        * String menjadi int
        */
        int rotaryPos = rotary1.getSelectedIndex() + 1;
        int rotary2Pos = rotary2.getSelectedIndex() + 1;
        int rotary3Pos = rotary3.getSelectedIndex() + 1;
        //mendapatkan input dan membuat inputan menjadi huruf kapital
        String input = inputField.getText().toUpperCase();
        /*
        * StringBuilder digunakan untuk "membangun" string dari char yang telah
        * diproses satu persatu. Mengapa StringbBuilder kita inisialisasi dengan
        * input, karena untuk membuat string dengan panjang yang sama dengan 
        * inputan user, sehingga tidak mendapatkan exception "Index out of Bounds"
        */
        StringBuilder chiper = new StringBuilder(input);
        //Menentukan teks dienkripsi berapa kali sesuai dengan rotary3 (paling kiri)
        for(int j = 0;j<rotary3Pos;j++){
        //perulangan untuk mengambil setiap karakter pada input untuk di sandikan
        for(int i=0;i<input.length();i++){
            /*
            * Pembuatan variabel sementara untuk menampung data karakter ketika
            * sedang di proses
            */
            char temp = input.charAt(i);
            /*
            * Jika rotary1 (paling kanan) telah mencapai 26 maka ia akan kembali
            * ke 1 dan menambahkan nilai rotary2 (tengah) dengan 1 sehingga 
            * rotary2 juga akan berubah nilai nya, 
            */
            if(rotaryPos == 26){
                rotary2Pos += 1;
                /*
                * Jika posisi rotary2 telah sampai pada 26 maka rotary2 akan
                * dikembalikan pada posisi 1
                */
                if(rotary2Pos == 26){
                    rotary2Pos = 1;
                }
                rotaryPos = 1;
                //mengubah tampilan rotary menyesuaikan nilai yang telah diatur
                rotary1.setSelectedIndex(rotaryPos);
                rotary2.setSelectedIndex(rotary2Pos - 1);
            }
            /*jika karakter yang akan dienkripsi merupakan karakter spesial
            * misalkan tanda baca, maka karakter tersebut tidak akan di enkripsi.
            * Sebaliknya jika karakter tersebut bukan merupakan karakter spesial
            * maka karakter akan disandikan.
            */
            if(!helper.isSpecialCharacter(temp)){
                /*
                Penyandian terjadi disini, 
                */
                /*
                Pertama kita ubah karakter tersebut menjadi kode ASCII. dimana 
                ASCII untuk huruf A adalah 65, maka nilai perubahan tersebut kita
                kurangkan dengan 65 sehingga huruf A kini menjadi 0, sehingga 
                dapat lebih mudah dioperasikan
                */
                    int wait = temp - 65;
                    /*
                    Penyandian Pertama :
                    pertama kita geser huruf berdasarkan dengan posisi rotary2 
                    lalu kita gunakan modulo 26 agar nilai tidak lebih dari 26,
                    sehingga dalam "pengembalian" kode menjadi huruf, tidak 
                    memunculkan karakter selain huruf.
                    */
                    wait = (wait + rotary2Pos) % 26;
                    /*
                    Penyandian kedua :
                    Setelah digeser oleh rotary2 kemudian kode huruf akan di geser
                    lagi berdasarkan rotary1, lagi, nilai mendapatkan modulo 26
                    sehingga dalam "pengembalian" kode menjadi huruf, tidak 
                    memunculkan karakter selain huruf.
                    */
                    wait = (wait + rotaryPos) % 26;
                    //mengembalikan kode huruf menjadi huruf
                    temp = (char)(wait + 65);
            }
            /*kemudian mengubah huruf yang ada pada StringBuilder menjadi
            karakter yang telah kita sandikan
            */
            chiper.setCharAt(i,temp);
            /*mengubah posisi rotary
                Mengapa mengubah posisi rotary?
                Karena dengan mengubah posisi rotary akan membuat suatu huruf
                tertentu tidak di enkripsi dengan cara yang sama, sehingga akan
                lebih sulit untuk menemukan pola nya.
            */
            rotaryPos += 1;
            //mengubah pilihan rotary1
            rotary1.setSelectedIndex(rotaryPos - 1);

        }
        }
        output.setText(chiper.toString());
    }
    
   private void decrypt(){
       //mengambil setingan "kunci" enkripsi
        /*
        * Mengapa ditambah satu? karena untuk mendapatkan value dari dropdown
        * tersebut, sehingga tidak perlu melakukan perubahan tipe data dari 
        * String menjadi int
        */
       int rotaryPos = rotary1.getSelectedIndex()+1;
       int rotary2Pos = rotary2.getSelectedIndex() +1;
       int rotary3Pos = rotary3.getSelectedIndex() + 1;
       //mendapatkan inputan
       String input = inputField.getText();
       /*
        * StringBuilder digunakan untuk "membangun" string dari char yang telah
        * diproses satu persatu. Mengapa StringbBuilder kita inisialisasi dengan
        * input, karena untuk membuat string dengan panjang yang sama dengan 
        * inputan user, sehingga tidak mendapatkan exception "Index out of Bounds"
        */
       StringBuilder plainText = new StringBuilder(input.toUpperCase());
       //Menentukan teks di dekripsi berapa kali sesuai dengan rotary3 (paling kiri)
        for(int j = 0;j<rotary3Pos;j++){
       //perulangan untuk mengambil setiap karakter pada input untuk di sandikan
        for(int i = 0; i < input.length();i++){
            /*
            * Pembuatan variabel sementara untuk menampung data karakter ketika
            * sedang di proses
            */
           char temp = input.charAt(i);
           /*
            * Jika rotary1 (paling kanan) telah mencapai 26 maka ia akan kembali
            * ke 1 dan menambahkan nilai rotary2 (tengah) dengan 1 sehingga 
            * rotary2 juga akan berubah nilai nya, 
            */
           if(rotaryPos == 26){
                rotary2Pos += 1;
                /*
                * Jika posisi rotary2 telah sampai pada 26 maka rotary2 akan
                * dikembalikan pada posisi 1
                */
                if(rotary2Pos == 26){
                rotary2Pos = 1;
                }
                rotaryPos = 1;
             //mengubah tampilan rotary menyesuaikan nilai yang telah diatur
                rotary1.setSelectedIndex(rotaryPos);
                rotary2.setSelectedIndex(rotary2Pos - 1);
            }
            /*jika karakter yang akan di denkripsi merupakan karakter spesial
            * misalkan tanda baca, maka karakter tersebut tidak akan di denkripsi.
            * Sebaliknya jika karakter tersebut bukan merupakan karakter spesial
            * maka karakter akan di buka sandinya.
            */
           if(!helper.isSpecialCharacter(temp)){
            /*
            Penyandian terjadi disini, 
            */
            /*
            Pertama kita ubah karakter tersebut menjadi kode ASCII. dimana 
            ASCII untuk huruf A adalah 65, maka nilai perubahan tersebut kita
            kurangkan dengan 65 sehingga huruf A kini menjadi 0, sehingga 
            dapat lebih mudah dioperasikan
            */
           int wait = temp-65;
           /*
            Pembukaan Pertama :
            Menggeser kembali berdasarkan geseran oleh rotary 1
           */
           wait = (wait - rotaryPos) % 26;
           /*
           Pembukaan Kedua:
            Menggeser kembali berdasarkan geseran oleh rotary 2
           */
           wait = (wait - rotary2Pos)%26;
           /*apabila setelah hasil geser nilai nya menjadi negatif, maka
           26 akan kita kurangkan dengan nilai tersebut sehingga kode nilai
           menjadi seperti sebelumnya
           */
           if(wait < 0){
           wait = 26 + wait;
           }
           temp = (char)(wait + 65);
           }
           //same as above
           plainText.setCharAt(i,temp);
           rotaryPos += 1;
           rotary1.setSelectedIndex(rotaryPos -1);
       }
       }
       output.setText(plainText.toString());
   }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        decryptButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        topTitle = new javax.swing.JLabel();
        rotary2 = new javax.swing.JComboBox<>();
        rotary1 = new javax.swing.JComboBox<>();
        rotary3 = new javax.swing.JComboBox<>();
        lblInput = new javax.swing.JLabel();
        encryptButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        output = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        inputField = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(0, 0));
        setLocationByPlatform(true);

        decryptButton.setText("Dekripsi");
        decryptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                decryptButtonActionPerformed(evt);
            }
        });

        jButton1.setText("Salin Hasil");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        topTitle.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        topTitle.setText("Penyandi Teks Sederhana");

        rotary2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26" }));
        rotary2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotary2ActionPerformed(evt);
            }
        });

        rotary1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26" }));

        rotary3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26" }));

        lblInput.setText("Masukan");

        encryptButton.setText("Enkripsi");
        encryptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                encryptButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Hasil");

        output.setColumns(20);
        output.setRows(5);
        jScrollPane1.setViewportView(output);

        inputField.setColumns(20);
        inputField.setRows(5);
        jScrollPane2.setViewportView(inputField);

        jMenu1.setText("Ubah");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Bersihkan Semua");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Keluar");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Pilihan");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Bantuan");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Tentang Kami");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblInput)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(decryptButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(encryptButton))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 34, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(173, 173, 173)
                        .addComponent(rotary3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84)
                        .addComponent(rotary2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85)
                        .addComponent(rotary1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(260, 260, 260)
                        .addComponent(topTitle)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(topTitle)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rotary2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rotary1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rotary3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblInput)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(encryptButton)
                    .addComponent(decryptButton))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addGap(21, 21, 21))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void decryptButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_decryptButtonActionPerformed
        decrypt();
    }//GEN-LAST:event_decryptButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        StringSelection stringSelection = new StringSelection(output.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection,null);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void rotary2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotary2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rotary2ActionPerformed

    private void encryptButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_encryptButtonActionPerformed
        //action tombol enkrip ketika di klik
        encrypt();
    }//GEN-LAST:event_encryptButtonActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        //ketika menu clear all di klik merubah semua yang dapat diubah menjadi "kosong"
        inputField.setText("");
        output.setText("");
        rotary1.setSelectedIndex(0);        
        rotary2.setSelectedIndex(0);
        rotary3.setSelectedIndex(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        //keluar aplikasi
        System.exit(0);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        //memunculkan teks bantuan
        JOptionPane.showMessageDialog(null,
                "Bantuan :\n-Menyandikan Teks\n\tMasukkan teks pada form masukan, lalu " + 
                 "pilihlah kunci penyandian teks anda \npada 3 pilihan diatas form masukkan "+
                 "kemudian tekan tombol Enkripsi.\n"
                        + "INGAT UNTUK TIDAK MEMASUKKAN ANGKA, DAN KURANGI PENGGUANAAN\n"
                        + "TANDA BACA! "
                        + "\n-Membuka Teks Yang Tersandi\n\t" +
                 "Masukkan teks yang tersandikan pada form masukan, lalu pilihlah kunci " +
                 "yang \nsama persis ketika anda menyandikan teks tersebut, kemudian tekan" + 
                 " tombol \nDekripsi.\n\t-Salin Hasil\n Anda dapat menyalin hasil penyandian " + 
                 "maupun pembukaan sandi dengan \nmenekan tombol Salin Hasil",
                "Bantuan",
                JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        //memunculkan teks identitas
        JOptionPane.showMessageDialog(null,"Penyandi Teks Sederhana\n\n"
                + "oleh : \n"
                + "Nur Awalia Ramadhani (18520241024)\n"
                + "Anang Ma'ruf Budiyanto (18520244001)\n"
                + "Firdaus Galuh Prihasta (18520244002)\n"
                + "Yosia Ibnu Loka (18520244014)"
                ,"Tentang Kami",JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton decryptButton;
    private javax.swing.JButton encryptButton;
    private javax.swing.JTextArea inputField;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblInput;
    private javax.swing.JTextArea output;
    private javax.swing.JComboBox<String> rotary1;
    private javax.swing.JComboBox<String> rotary2;
    private javax.swing.JComboBox<String> rotary3;
    private javax.swing.JLabel topTitle;
    // End of variables declaration//GEN-END:variables
}

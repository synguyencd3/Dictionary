import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MyPanel extends JPanel implements ActionListener {
    private JTextArea jText;
    private JTextField jWord;
    private JButton jXoa;
    private JButton jTraCuu;
    private JTextField jTuNgay;
    private JTextField jDenNgay;
    private JButton jThem;
    private JTextArea jText2;
    private JToggleButton jToggle;
    private JButton jYeuThich;
    private JButton jThongKe;

    Dict Anh_Viet;
    Dict Viet_Anh;
    Dict pointer;

    public MyPanel() {
        //construct components
        jText = new JTextArea (5, 5);
        jWord = new JTextField (5);
        jXoa = new JButton ("Xoa");
        jXoa.addActionListener(this);
        jTraCuu = new JButton ("Tra cứu");
        jTraCuu.addActionListener(this);
        jTuNgay = new JTextField (5);
        jDenNgay = new JTextField (5);
        jThem = new JButton ("Them vao");
        jThem.addActionListener(this);
        jText2 = new JTextArea (5, 5);
        jToggle = new JToggleButton ("Anh->Vie", false);
        jToggle.addActionListener(this);
        jYeuThich = new JButton ("Yeu thich");
        jThongKe = new JButton ("Thống kê");
        jThongKe.addActionListener(this);

        //adjust size and set layout
        setPreferredSize (new Dimension (681, 376));
        setLayout (null);

        //
        JScrollPane scrollPane1 = new JScrollPane(jText);
        JScrollPane scrollPane2 = new JScrollPane(jText2);

        //add components
        add (scrollPane1);
        add (scrollPane2);
        add (jWord);
        add (jXoa);
        add (jTraCuu);
        add (jTuNgay);
        add (jDenNgay);
        add (jThem);
        add (jToggle);
        add (jYeuThich);
        add (jThongKe);

        //set component bounds (only needed by Absolute Positioning)
        scrollPane1.setBounds (25, 90, 210, 200);
        jWord.setBounds (25, 30, 210, 30);
        jXoa.setBounds (265, 165, 100, 25);
        jTraCuu.setBounds (265, 75, 100, 25);
        jTuNgay.setBounds (390, 35, 115, 25);
        jDenNgay.setBounds (390, 75, 115, 25);
        jThem.setBounds (265, 120, 100, 25);
        scrollPane2.setBounds (390, 135, 170, 120);
        jToggle.setBounds (265, 35, 100, 25);
        jYeuThich.setBounds (265, 215, 100, 25);
        jThongKe.setBounds (545, 50, 100, 25);

        Anh_Viet = new Dict("Anh_Viet");
        Viet_Anh = new Dict("Viet_Anh");
        pointer=Anh_Viet;
    }


    public static void main (String[] args) {
        JFrame frame = new JFrame ("MyPanel");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (new MyPanel());
        frame.setResizable(false);
        frame.pack();
        frame.setVisible (true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()== jTraCuu)
        {
            System.out.println("tra cuu");
            String source= jWord.getText();
            jText.setText(pointer.Translate(source));
        }
        if (e.getSource()==jThongKe)
        {
            String str1=jTuNgay.getText();
            String str2=jDenNgay.getText();
            jText2.setText(Dict.ThongKe(str1,str2));
        }
        if (e.getSource()==jToggle)
        {
            AbstractButton abstractButton =
                    (AbstractButton)e.getSource();

            boolean selected = abstractButton.getModel().isSelected();
            if (selected)
            {
                pointer=Viet_Anh;
                jToggle.setText("Vie->Anh");
                System.out.println("Vie->Anh");
            }
            else
            {
                pointer=Anh_Viet;
                jToggle.setText("Anh->Vie");
                System.out.println("Anh->Vie");
            }

            System.out.println("Action - selected=" + selected + "\n");
        }
        if (e.getSource()==jXoa)
        {
            String word= jWord.getText();
            pointer.Delete(word);
        }
        if (e.getSource()==jThem)
        {
            String word = jWord.getText();
            String meaning= jText.getText();
            pointer.Add(word,meaning);
        }
    }
}


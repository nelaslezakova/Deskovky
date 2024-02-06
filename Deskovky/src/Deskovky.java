import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Deskovky extends JFrame {
    private JTextField txtName;
    private JCheckBox vlastneno;
    private JRadioButton rB1;
    private JRadioButton rB2;
    private JRadioButton rB3;
    private JButton bPredchozi;
    private JButton bDalsi;
    private JButton bUlozit;
    private JPanel panel;
    private int index = 0;
    private int vybraneHodnoceni;

    private List<DeskoveHry> deskoveHry = new ArrayList<>();

    public DeskoveHry getDeskoveHry(int i){
        return deskoveHry.get(i);
    }
    public Deskovky(){
        setContentPane(panel);
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Deskové Hry");


        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(rB1);
        btnGroup.add(rB2);
        btnGroup.add(rB3);
        rB1.addItemListener(e -> handleRadioButtonClick(1));
        rB2.addItemListener(e -> handleRadioButtonClick(2));
        rB3.addItemListener(e -> handleRadioButtonClick(3));

        bPredchozi.addActionListener(e -> {
            if (index > 0){
                index--;
                zobrazDeskoveHry(getDeskoveHry(index));
            }
        });
        bDalsi.addActionListener(e -> {
            if (index < deskoveHry.size() - 1) {
                index++;
                zobrazDeskoveHry(getDeskoveHry(index));
            }
        });
        bUlozit.addActionListener(e -> ulozDoSouboru());
        cteniZeSouboru();
        if (!deskoveHry.isEmpty()){
            zobrazDeskoveHry(getDeskoveHry(index));
        } else {
            JOptionPane.showMessageDialog(this, "List je prázdný", "Chyba", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public void cteniZeSouboru() {
        try (Scanner sc = new Scanner(new BufferedReader(new FileReader("deskovky.txt")))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(";");
                String jmeno = (parts[0]);
                boolean vlastneno = parts[1].equals("vlastněno");
                int hodnoceni = Integer.parseInt(parts[2]);
                DeskoveHry dh = new DeskoveHry(jmeno, vlastneno, hodnoceni);
                deskoveHry.add(dh);
            }
        } catch (FileNotFoundException e) {
            System.err.println("složka nebyla nalezena: " + e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            System.err.println("špatné formátování čísel: " + e.getLocalizedMessage());
        }
    }
    private void handleRadioButtonClick(int rating) {
        vybraneHodnoceni = rating;
    }
    public void ulozDoSouboru() {
        DeskoveHry zobrazeniDH = deskoveHry.get(index);
        zobrazeniDH.setJmeno(txtName.getText());
        zobrazeniDH.setVlastneno(vlastneno.isSelected());
        zobrazeniDH.setHodnoceni(vybraneHodnoceni);

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("deskovky.txt")))) {
            for (DeskoveHry dh: deskoveHry) {
                writer.println(dh.getJmeno() + ";" + (dh.isVlastneno() ? "vlastněno" : "nevlastněno") + ";" + dh.getHodnoceni());
            }
            JOptionPane.showMessageDialog(this, "Změny byli uloženy.", "Message saved", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            System.err.println("Chyba při ukládání do souboru " + e.getLocalizedMessage());
        }
    }
    public void zobrazDeskoveHry(DeskoveHry dh){
        txtName.setText(dh.getJmeno());
        vlastneno.setSelected(dh.isVlastneno());
        switch (dh.getHodnoceni()){
            case 1 -> rB1.setSelected(true);
            case 2 -> rB2.setSelected(true);
            case 3 -> rB3.setSelected(true);
        }
    }
    public static void  main(String[] args) {
        Deskovky d = new Deskovky();
        d.setVisible(true);
    }
}

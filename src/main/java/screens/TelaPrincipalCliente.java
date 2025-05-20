package screens;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import utils.MedicationsUtilities;

public class TelaPrincipalCliente {

    public TelaPrincipalCliente() {

        final Color azulPharmatech = new Color(1, 0, 127);
        final Color cinzaFundo = new Color (207, 206, 206);

        // TELA  
        final JFrame tela = new JFrame("Tela Principal");
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setSize(1500, 800);
        tela.setLocationRelativeTo(null);

        // CONTAINER PRINCIPAL
        final JPanel container = new JPanel(); 
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(cinzaFundo);
        
        // CABECALHO
        final JPanel cabecalho = new JPanel();
        cabecalho.setLayout(null);
        cabecalho.setPreferredSize(new Dimension(1500, 100));
        cabecalho.setBackground(cinzaFundo);
        cabecalho.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
        
        // LOGO 
        ImageIcon imagemLogo = new ImageIcon("./img/icon.png");

        imagemLogo = new ImageIcon(
            imagemLogo.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)
        );
         
        final JLabel labelLogo = new JLabel(imagemLogo);
        labelLogo.setBounds(20, 5, 80, 80);
        cabecalho.add(labelLogo);

        // TITULO 
        final JLabel msgPharmatech = new JLabel("Pharmatech");
        msgPharmatech.setBounds(120, 25, 500, 50);
        msgPharmatech.setFont(new Font("Arial", Font.BOLD, 34));
        msgPharmatech.setForeground(azulPharmatech);
        cabecalho.add(msgPharmatech);

        // BARRA DE PESQUISA 
        final JTextField barraPesquisa = new JTextField("  Procure por um Medicamento...");
        barraPesquisa.setBounds(450, 25, 500, 50); 
        barraPesquisa.setFont(new Font("Arial", Font.PLAIN, 25));
        cabecalho.add(barraPesquisa);

        barraPesquisa.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(final MouseEvent mouseEvent) {
                barraPesquisa.setText("  ");
            }

        }); 

        // CONFIGURAÇÃO DOS BOTÕES 
        final Font defaultFont = new Font("Helvetica", Font.BOLD, 20);
        final Map<TextAttribute, Object> attributes = (Map<TextAttribute, Object>) defaultFont.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

        // BOTAO CARRINHO 
        final JButton botaoCarrinho = new JButton("Carrinho");
        botaoCarrinho.setBounds(1300, 25, 140, 40);
        botaoCarrinho.setContentAreaFilled(false);
        botaoCarrinho.setBorderPainted(false);
        botaoCarrinho.setOpaque(false);
        botaoCarrinho.setFont(botaoCarrinho.getFont().deriveFont(attributes));
        botaoCarrinho.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cabecalho.add(botaoCarrinho);

        // BOTAO CADASTRAR 
        final JButton botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.setBounds(1150, 25, 140, 40);
        botaoCadastrar.setContentAreaFilled(false);
        botaoCadastrar.setBorderPainted(false);
        botaoCadastrar.setOpaque(false);
        botaoCadastrar.setFont(botaoCadastrar.getFont().deriveFont(attributes));
        botaoCadastrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));        
        cabecalho.add(botaoCadastrar);

        // BOTAO LOGAR 
        final JButton botaoLogar = new JButton("Logar");
        botaoLogar.setBounds(1000, 25, 140, 40);
        botaoLogar.setContentAreaFilled(false);
        botaoLogar.setBorderPainted(false);
        botaoLogar.setOpaque(false);
        botaoLogar.setFont(botaoLogar.getFont().deriveFont(attributes));
        botaoLogar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));        
        cabecalho.add(botaoLogar);
        
        container.add(cabecalho);
        
        // PAINEL EXIBIÇÃO MEDICAMENTOS
        final JPanel painelMedicamentos = new JPanel();
        painelMedicamentos.setLayout(new BoxLayout(painelMedicamentos, BoxLayout.Y_AXIS));
        painelMedicamentos.setBackground(cinzaFundo);
        painelMedicamentos.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        container.add(painelMedicamentos);

        botaoLogar.addActionListener((event) -> {
            new TelaLogin();
            tela.dispose();
        });

        botaoCadastrar.addActionListener((event) -> {
            new TelaCadastro();
            tela.dispose();
        });
        botaoCarrinho.addActionListener((event) -> {
            new TelaCarrinho();
            tela.dispose();
        });

        MedicationsUtilities.loadMedications(painelMedicamentos);

        final JScrollPane jScrollPane = new JScrollPane(container);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.getVerticalScrollBar().setUnitIncrement(16); // scroll mais suave

        tela.add(jScrollPane);
        tela.setVisible(true);

    }    

}

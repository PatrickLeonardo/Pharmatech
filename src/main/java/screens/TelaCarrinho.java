package screens;
import java.awt.*;
import javax.swing.*;
public class TelaCarrinho {

    public TelaCarrinho() {

        Color cinzaFundo = new Color (207, 206, 206);
        Color azulPharmatech = new Color(1, 0, 127);

        ImageIcon logo = new ImageIcon("C:\\Users\\oushe\\Downloads\\simboloFarmacia.jpeg");
        Image imagemRedimensionada = logo.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        logo = new ImageIcon(imagemRedimensionada);

        final JLabel labelLogo = new JLabel(logo);
        final JLabel msgPesquisa = new JLabel("Pharmatech");
        final JFrame tela = new JFrame("Tela Carrinho");
        final JButton botaoTelaPrincipal = new JButton("Tela Principal");
        final JLabel labelProduto = new JLabel("Produto: ");
        final JLabel labelQuantidade = new JLabel("Quantidade: ");
        final JTextField textoProduto = new JTextField(30);
        final JTextField textoQuantidade = new JTextField(30);
        final JButton botaoAdicionarCarrinho = new JButton("Adicionar ao Carrinho");
        final JButton botaoFinalizarCompra = new JButton("Finalizar Compra");

        final Container container = tela.getContentPane();
        container.setLayout(null);
        container.setBackground(cinzaFundo);

        labelLogo.setBounds(20, 35, 30, 30);
        container.add(labelLogo);

        msgPesquisa.setBounds(50, 25, 500, 50);
        msgPesquisa.setFont(new Font("Arial", Font.PLAIN, 30));
        msgPesquisa.setForeground(azulPharmatech);
        container.add(msgPesquisa);

        botaoTelaPrincipal.setBounds(100, 620, 250, 100);
        container.add(botaoTelaPrincipal);

        botaoAdicionarCarrinho.setBounds(600, 620, 250, 100);
        container.add(botaoAdicionarCarrinho);

        botaoFinalizarCompra.setBounds(1100, 620, 250, 100);
        container.add(botaoFinalizarCompra);

        labelProduto.setBounds(100, 150, 500, 200);
        labelProduto.setFont(new Font("Arial", Font.PLAIN, 30));
        container.add(labelProduto);

        labelQuantidade.setBounds(100, 350, 500, 200);
        labelQuantidade.setFont(new Font("Arial", Font.PLAIN, 30));
        container.add(labelQuantidade);

        textoProduto.setBounds(300, 200, 500, 100);
        textoProduto.setFont(new Font("Arial", Font.PLAIN, 30));    
        container.add(textoProduto);

        textoQuantidade.setBounds(300, 400, 500, 100);  
        textoQuantidade.setFont(new Font("Arial", Font.PLAIN, 30));
        container.add(textoQuantidade);
       
        
        container.add(botaoFinalizarCompra);
        

        botaoAdicionarCarrinho.addActionListener((event) -> {
            String produto = textoProduto.getText();
            String quantidade = textoQuantidade.getText();
            JOptionPane.showMessageDialog(tela, "Produto: " + produto + "\nQuantidade: " + quantidade + " adicionado ao carrinho.");
        });

        botaoFinalizarCompra.addActionListener((event) -> {
            JOptionPane.showMessageDialog(tela, "Compra finalizada com sucesso!");
        });

        botaoTelaPrincipal.addActionListener((event) -> {
            tela.dispose();;
            new TelaPrincipalCliente();
        });

        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setContentPane(container);
        tela.pack();
        tela.setSize(1500, 800);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);

    }

}

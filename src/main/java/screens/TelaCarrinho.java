package screens;
import java.awt.*;
import javax.swing.*;
public class TelaCarrinho {

    public TelaCarrinho() {

        Color cinzaFundo = new Color (207, 206, 206);

        final JFrame tela = new JFrame("Tela Carrinho");
        final JButton botaoTelaPrincipal = new JButton("Tela Principal");
        final JLabel labelProduto = new JLabel("Produto: ");
        final JTextField textoProduto = new JTextField(30);
        final JLabel labelQuantidade = new JLabel("Quantidade: ");
        final JTextField textoQuantidade = new JTextField(30);
        final JButton botaoAdicionarCarrinho = new JButton("Adicionar ao Carrinho");
        final JButton botaoFinalizarCompra = new JButton("Finalizar Compra");

        final Container container = tela.getContentPane();
        container.setLayout(new GridLayout(5, 10));
        container.setBackground(cinzaFundo);

        

        container.add(labelProduto);
        container.add(textoProduto);
        container.add(labelQuantidade);
        container.add(textoQuantidade);
        container.add(botaoAdicionarCarrinho);
        container.add(botaoFinalizarCompra);
        container.add(botaoTelaPrincipal);

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

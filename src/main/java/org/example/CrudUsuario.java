package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CrudUsuario {

    private JTextField nomeField;
    private JTextField emailField;
    private JTextField dataNascimentoField;
    private JTextField enderecoField;
    private JTextField telefoneField;
    private JButton btnCriar;
    private JButton btnEditar;
    private JButton btnDeletar;
    private JList<String> usuarioList;
    private DefaultListModel<String> listModel;

    private UsuarioDAO usuarioDAO;

    public CrudUsuario() {
        usuarioDAO = new UsuarioDAO();

        JFrame frame = new JFrame("CRUD de Usuário");

        nomeField = new JTextField(20);
        emailField = new JTextField(20);
        dataNascimentoField = new JTextField(20);
        enderecoField = new JTextField(20);
        telefoneField = new JTextField(20);

        btnCriar = new JButton("Criar");
        btnEditar = new JButton("Editar");
        btnDeletar = new JButton("Deletar");

        btnCriar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarUsuario();
            }
        });

        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarUsuario();
            }
        });

        btnDeletar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletarUsuario();
            }
        });

        listModel = new DefaultListModel<>();
        usuarioList = new JList<>(listModel);
        usuarioList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        usuarioList.setLayoutOrientation(JList.VERTICAL);

        JScrollPane listScrollPane = new JScrollPane(usuarioList);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        inputPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx++;
        inputPanel.add(nomeField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx++;
        inputPanel.add(emailField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Data de Nascimento:"), gbc);
        gbc.gridx++;
        inputPanel.add(dataNascimentoField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Endereço:"), gbc);
        gbc.gridx++;
        inputPanel.add(enderecoField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx++;
        inputPanel.add(telefoneField, gbc);
        gbc.gridx++;
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanel.add(btnCriar, gbc);
        gbc.gridx++;
        inputPanel.add(btnEditar, gbc);
        gbc.gridx++;
        inputPanel.add(btnDeletar, gbc);

        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(new JLabel("Usuários:"), BorderLayout.NORTH);
        listPanel.add(listScrollPane, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(listPanel, BorderLayout.CENTER);

        frame.add(mainPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);

        refreshUsuarioList();
    }

    private void refreshUsuarioList() {
        List<Usuario> usuarios = usuarioDAO.listarUsuarios();
        listModel.clear();
        for (Usuario usuario : usuarios) {
            listModel.addElement(
                    String.format("%s - %s - %s - %s - %s",
                            usuario.getNome(),
                            usuario.getEmail(),
                            usuario.getDataNascimento(),
                            usuario.getEndereco(),
                            usuario.getTelefone()
                    )
            );
        }
    }

    private void criarUsuario() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String dataNascimento = dataNascimentoField.getText();
        String endereco = enderecoField.getText();
        String telefone = telefoneField.getText();
        Usuario usuario = new Usuario(0, nome, email, dataNascimento, endereco, telefone);
        usuarioDAO.criarUsuario(usuario);
        refreshUsuarioList();
    }

    private void editarUsuario() {
        int selectedIndex = usuarioList.getSelectedIndex();
        if (selectedIndex != -1) {
            Usuario usuario = usuarioDAO.listarUsuarios().get(selectedIndex);
            String nome = nomeField.getText();
            String email = emailField.getText();
            String dataNascimento = dataNascimentoField.getText();
            String endereco = enderecoField.getText();
            String telefone = telefoneField.getText();
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setDataNascimento(dataNascimento);
            usuario.setEndereco(endereco);
            usuario.setTelefone(telefone);
            usuarioDAO.atualizarUsuario(usuario);
            refreshUsuarioList();
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um usuário para editar.");
        }
    }

    private void deletarUsuario() {
        int selectedIndex = usuarioList.getSelectedIndex();
        if (selectedIndex != -1) {
            Usuario usuario = usuarioDAO.listarUsuarios().get(selectedIndex);
            usuarioDAO.deletarUsuario(usuario.getId());
            refreshUsuarioList();
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um usuário para deletar.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CrudUsuario();
            }
        });
    }
}

package org.example;

import org.example.UsuarioDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CrudUsuario extends JFrame {

    private JTextField nomeField;
    private JTextField emailField;
    private JButton btnCriar;
    private JButton btnEditar;
    private JButton btnDeletar;
    private JList<String> usuarioList;
    private DefaultListModel<String> listModel;

    private UsuarioDAO usuarioDAO;

    public CrudUsuario() {
        super("CRUD de Usuário");
        usuarioDAO = new UsuarioDAO();

        nomeField = new JTextField(20);
        emailField = new JTextField(20);

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

        add(mainPanel);

        refreshUsuarioList();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setVisible(true);
    }

    private void refreshUsuarioList() {
        List<Usuario> usuarios = usuarioDAO.listarUsuarios();
        listModel.clear();
        for (Usuario usuario : usuarios) {
            listModel.addElement(usuario.getNome() + " - " + usuario.getEmail());
        }
    }

    private void criarUsuario() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        Usuario usuario = new Usuario(0, nome, email);
        usuarioDAO.criarUsuario(usuario);
        refreshUsuarioList();
    }

    private void editarUsuario() {
        int selectedIndex = usuarioList.getSelectedIndex();
        if (selectedIndex != -1) {
            Usuario usuario = usuarioDAO.listarUsuarios().get(selectedIndex);
            String nome = nomeField.getText();
            String email = emailField.getText();
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuarioDAO.atualizarUsuario(usuario);
            refreshUsuarioList();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para editar.");
        }
    }

    private void deletarUsuario() {
        int selectedIndex = usuarioList.getSelectedIndex();
        if (selectedIndex != -1) {
            Usuario usuario = usuarioDAO.listarUsuarios().get(selectedIndex);
            usuarioDAO.deletarUsuario(usuario.getId());
            refreshUsuarioList();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para deletar.");
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

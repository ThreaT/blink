package deprecating;

import java.util.ArrayList;
import java.util.List;

public class ScenarioCreator extends javax.swing.JFrame {

    private String file = "";
    private String packageName;
    private String imports;
    private String clazz;
    private String intention;
    private String scenario;
    private String fit;
    private List<Condition> fitConditions = new ArrayList<>();
    private String main;
    private String test;
    private String template;

    public ScenarioCreator() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jCheckBox3 = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Scenario Creator");

        jPanel1.setBackground(new java.awt.Color(239, 242, 253));

        jTextField5.setText("Action");
        jTextField5.setToolTipText("Action...");
        jTextField5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField5FocusGained(evt);
            }
        });
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jTextField6.setText("Url");
        jTextField6.setToolTipText("Url...");
        jTextField6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField6FocusGained(evt);
            }
        });
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        jLabel6.setText("i.e. Home");

        jCheckBox3.setText("Include Trailing");

        jLabel7.setText("i.e. home");

        jLabel8.setText("i.e. read");

        jLabel9.setText("i.e. /home");

        jTextField7.setText("Class");
        jTextField7.setToolTipText("Name...");
        jTextField7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField7FocusGained(evt);
            }
        });
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        jTextField8.setText("Feature");
        jTextField8.setToolTipText("Feature...");
        jTextField8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField8FocusGained(evt);
            }
        });
        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });

        jTextField1.setText("Application name");
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField1FocusGained(evt);
            }
        });
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel1.setText("i.e. HelloWorld");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField7, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField8, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox3)
                .addGap(40, 40, 40))
        );

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Request Parameters", "Regular Expression", "Nullable"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("package com.yourapp.scenario.home.read;\n\nimport com.blink.Feature;\nimport com.blink.Intention;\nimport com.blink.Protocol;\nimport com.blink.Report;\nimport com.blink.Scenario;\nimport com.blink.WebServer;\nimport java.io.IOException;\nimport java.util.logging.Level;\nimport java.util.logging.Logger;\nimport javax.servlet.http.HttpServlet;\nimport javax.servlet.http.HttpServletRequest;\nimport javax.servlet.http.HttpServletResponse;\nimport javax.websocket.Session;\n\npublic class Home extends HttpServlet implements Feature {\n\n    private static final Intention intention = new Intention(Protocol.HTTP, \"\", \"\", \"/home\", true);\n    public static final Scenario scenario = new Scenario(Home.class, intention);\n\n    @Override\n    public Boolean fit(Session session, String message, HttpServletRequest request, HttpServletResponse response) {\n        Logger.getLogger(Home.class.getName()).log(Level.FINEST, \"Running Home: fit(Session session, String message, HttpServletRequest request, HttpServletResponse response)\");\n        return true;\n    }\n\n    @Override\n    public void main(Session session, String message, HttpServletRequest request, HttpServletResponse response) {\n        try {\n            Logger.getLogger(Home.class.getName()).log(Level.FINEST, \"Running Home: main(Session session, String message, HttpServletRequest request, HttpServletResponse response)\");\n            response.getWriter().println(template(session, message, request, response));\n        } catch (IOException ex) {\n            Logger.getLogger(Home.class.getName()).log(CustomLevel.HIGHEST, null, ex);\n        }\n    }\n\n    /**\n     * Acceptance requirements:\n     *\n     * <ol>\n     * <li>\n     * Ensure that processing completes in under 10 ms.\n     * </li>\n     * </ol>\n     *\n     * @param session Session\n     * @param message String\n     * @param request HttpServletRequest\n     * @param response HttpServletResponse\n     * @return Report\n     */\n    @Override\n    public Report test(Session session, String message, HttpServletRequest request, HttpServletResponse response) {\n        Logger.getLogger(Home.class.getName()).log(Level.FINEST, \"Running Home: test(Session session, String message, HttpServletRequest request, HttpServletResponse response)\");\n        Report report = new Report(1, 1, \"100%\", \"\");\n        Long start = System.currentTimeMillis();\n        main(session, message, request, response);\n        Long end = System.currentTimeMillis();\n        if (end - start > 10) {\n            report.setSuccessful(report.getSuccessful() - 1);\n        }\n        report.setPercentage(report.calculatePercentage(report.getTotal(), report.getSuccessful()));\n        return report;\n    }\n\n    public static String template(Session session, String message, HttpServletRequest request, HttpServletResponse response) {\n        \n    }\n}\n");
        jTextArea1.setToolTipText("Html...");
        jTextArea1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea1FocusGained(evt);
            }
        });
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("Create");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 615, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed

    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jTextField7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField7FocusGained
        jTextField7.setText("");
    }//GEN-LAST:event_jTextField7FocusGained

    private void jTextField8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField8FocusGained
        jTextField8.setText("");
    }//GEN-LAST:event_jTextField8FocusGained

    private void jTextField5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField5FocusGained
        jTextField5.setText("");
    }//GEN-LAST:event_jTextField5FocusGained

    private void jTextField6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField6FocusGained
        jTextField6.setText("");
    }//GEN-LAST:event_jTextField6FocusGained

    private void jTextArea1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea1FocusGained

    }//GEN-LAST:event_jTextArea1FocusGained

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        populateFitConditions();
        System.out.println("fit conditions: " + fitConditions.toString());
        this.packageName = "package com." + jTextField1.getText().toLowerCase() + ".scenario." + jTextField7.getText().toLowerCase() + "." + jTextField5.getText().toLowerCase() + "\n\n";
        this.imports = "import com.blink.Feature;\n"
                + "import com.blink.Intention;\n"
                + "import com.blink.Protocol;\n"
                + "import com.blink.Report;\n"
                + "import com.blink.Scenario;\n"
                + "import com.blink.WebServer;\n"
                + "import java.io.IOException;\n"
                + "import java.util.logging.Level;\n"
                + "import java.util.logging.Logger;\n"
                + "import java.util.regex.Pattern;\n"
                + "import javax.servlet.http.HttpServlet;\n"
                + "import javax.servlet.http.HttpServletRequest;\n"
                + "import javax.servlet.http.HttpServletResponse;\n"
                + "import javax.websocket.Session;\n\n";
        this.clazz = "public class " + jTextField7.getText() + " extends HttpServlet implements Feature {\n\n";
        this.intention = "private static final Intention intention = new Intention(Protocol.HTTP, " + "\"" + jTextField8.getText() + "\", " + "\"" + jTextField5.getText() + "\", " + "\"" + jTextField6.getText() + "\", " + jCheckBox3.isSelected() + ");\n";
        this.scenario = "public static final Scenario scenario = new Scenario(" + jTextField7.getText() + ".class, " + "intention);\n\n";
        this.fit = "@Override\npublic Boolean fit(Session session, String message, HttpServletRequest request, HttpServletResponse response) {\n" + fitConditions(fitConditions) + "\n}\n\n";
        this.main = "@Override\npublic void main(Session session, String message, HttpServletRequest request, HttpServletResponse response) {\n"
                + "try {\n"
                + "response.getWriter().println(template(session, message, request, response));\n"
                + "} catch (IOException ex) {}" + "\n}\n\n";
        this.test = "  /**\n"
                + "     * Acceptance requirements:\n"
                + "     *\n"
                + "     * <ol>\n"
                + "     * <li>\n"
                + "     * None.\n"
                + "     * </li>\n"
                + "     * </ol>\n"
                + "     *\n"
                + "     * @param session Session\n"
                + "     * @param message String\n"
                + "     * @param request HttpServletRequest\n"
                + "     * @param response HttpServletResponse\n"
                + "     * @return Report\n"
                + "     */\n"
                + "    @Override\n"
                + "    public Report test(Session session, String message, HttpServletRequest request, HttpServletResponse response) {\n"
                + "        return null;\n"
                + "    }\n\n";
        this.template = "public static String template(Session session, String message, HttpServletRequest request, HttpServletResponse response) {\n"
                + "return \"\";"
                + "        \n"
                + "    }\n";
        this.file = this.packageName + this.imports + this.clazz + this.intention + this.scenario + this.fit + this.main + this.test + this.template;
        jTextArea1.setText(this.file);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed

    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed

    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed

    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusGained
        jTextField1.setText("");
    }//GEN-LAST:event_jTextField1FocusGained

    public class Condition {

        private String requestParameter;
        private String regularExpression;
        private Boolean nullable;

        public Condition() {
        }

        public Condition(String requestParameter, String regularExpression, Boolean nullable) {
            this.requestParameter = requestParameter;
            this.regularExpression = regularExpression;
            this.nullable = nullable;
        }

        public String getRequestParameter() {
            return requestParameter;
        }

        public void setRequestParameter(String requestParameter) {
            this.requestParameter = requestParameter;
        }

        public String getRegularExpression() {
            return regularExpression;
        }

        public void setRegularExpression(String regularExpression) {
            this.regularExpression = regularExpression;
        }

        public Boolean getNullable() {
            return nullable;
        }

        public void setNullable(Boolean nullable) {
            this.nullable = nullable;
        }

        @Override
        public String toString() {
            return "Condition{" + "requestParameter=" + requestParameter + ", regularExpression=" + regularExpression + ", nullable=" + nullable + '}';
        }
    }

    public void populateFitConditions() {
        fitConditions.clear();
        Integer numberOfRows = jTable2.getRowCount();
        for (int i = 0; i < numberOfRows; i++) {
            try {
                jTable2.getValueAt(i, 0).toString();
            } catch (NullPointerException nullPointerException) {
                break;
            }
            String requestParameter = jTable2.getValueAt(i, 0).toString();
            String regularExpression = jTable2.getValueAt(i, 1).toString();
            Boolean nullable = true;
            try {
                jTable2.getValueAt(i, 2).toString();
            } catch (NullPointerException nullPointerException) {
                nullable = false;
            }
            Condition condition = new Condition(requestParameter, regularExpression, nullable);
            fitConditions.add(condition);
        }
    }

    public String fitConditions(List<Condition> conditions) {
        String fitConditions2 = "";

        if (conditions.isEmpty()) {
            fitConditions2 = "return true;";
            return fitConditions2;
        }

        fitConditions2 += "return (";

        for (int i = 0; i < conditions.size(); i++) {
            String fitCondition = "";

            if (!conditions.get(i).getNullable()) {
                fitCondition += "((request.getParameter(\"" + conditions.get(i).getRequestParameter() + "\") != null) && ";
            }
            fitCondition += "(Pattern.matches(\"" + conditions.get(i).getRequestParameter() + "\", request.getParameter(\"" + conditions.get(i).getRegularExpression() + "\")))";
            if (!conditions.get(i).getNullable()) {
                fitCondition += ")";
            }

            fitConditions2 += fitCondition;
            if (i == (conditions.size() - 1)) {
                fitConditions2 += ");";
            } else {
                fitConditions2 += " &&\n";
            }
        }

        return fitConditions2;
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ScenarioCreator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ScenarioCreator().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    // End of variables declaration//GEN-END:variables
}

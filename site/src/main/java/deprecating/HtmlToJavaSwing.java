package cool.blink.site.htmltojava.create;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import net.htmlparser.jericho.Attribute;
import net.htmlparser.jericho.Attributes;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

public class HtmlToJavaSwing extends javax.swing.JFrame {

    public Map<String, Integer> elementQuantity = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    public Integer unsupportedElements = 0;
    public List<ElementWrapper> elementWrappers = new ArrayList<>();

    public HtmlToJavaSwing() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HtmlToJava");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jButton1.setText("Convert to Java Code");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("HTML:");

        jLabel2.setText("Java:");

        jButton2.setText("Copy to Clipboard");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(411, 411, 411))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(12, 12, 12)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jTextArea2.setText(convertAllElements(jTextArea1.getText()));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        StringSelection stringSelection = new StringSelection(jTextArea2.getText());
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(stringSelection, null);
    }//GEN-LAST:event_jButton2ActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HtmlToJavaSwing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HtmlToJavaSwing().setVisible(true);
            }
        });
    }

    public String convertAllElements(String payload) {
        String java = "";
        Source source = new Source(payload);
        List<Element> allElements = source.getAllElements();
        for (Element element : allElements) {
            ElementWrapper elementWrapper;
            if (element.getDepth() < 2) {
                elementWrapper = new ElementWrapper(element, element.getName());
            } else {
                elementWrapper = new ElementWrapper(element, element.getName() + "_" + instanceCount(element) + "_" + element.getDepth());
            }
            elementWrappers.add(elementWrapper);
        }
        for (ElementWrapper elementWrapper : elementWrappers) {
            if (isElementSupported(elementWrapper.element)) {
                java += createSupportedInstance(elementWrapper);
            } else {
                java += createUnsupportedInstance(elementWrapper);
            }
        }
        return java;
    }

    public String[] getSupportedElementNames() {
        Reflections reflections = new Reflections("cool.blink.front.html.element", new SubTypesScanner(false));
        Set<Class<? extends cool.blink.front.html.Element>> allClasses = reflections.getSubTypesOf(cool.blink.front.html.Element.class);
        String[] supportedElementNames = new String[allClasses.size()];
        int index = 0;
        for (Class clazz : allClasses) {
            supportedElementNames[index] = clazz.getSimpleName();
            index++;
        }
        return supportedElementNames;
    }

    public Boolean isElementSupported(Element element) {
        String[] supportedElements = getSupportedElementNames();
        for (String supportedElement : supportedElements) {
            if (supportedElement.equalsIgnoreCase(element.getName())) {
                return true;
            }
        }
        return false;
    }

    public String createUnsupportedInstance(ElementWrapper elementWrapper) {
        unsupportedElements++;
        return "//Unsupported " + stripIllegalCharacters(elementWrapper.element.getName()) + unsupportedElements + " = new UnsupportedElement();\n";
    }

    public String createSupportedInstance(ElementWrapper elementWrapper) {
        String instance = stripIllegalCharacters(oneWordToProperCase(elementWrapper.element.getName())) + " " + stripIllegalCharacters(elementWrapper.instanceName) + " = " + "(" + stripIllegalCharacters(oneWordToProperCase(elementWrapper.element.getName())) + ")" + " new " + stripIllegalCharacters(oneWordToProperCase(elementWrapper.element.getName())) + "()";
        if (getId(elementWrapper.element) != null) {
            instance += ".setId(" + getId(elementWrapper.element) + ")";
        }
        if (getClass(elementWrapper.element) != null) {
            instance += ".setClazz(" + getClass(elementWrapper.element) + ")";
        }
        if (getStyle(elementWrapper.element) != null) {
            instance += ".setStyle(" + getStyle(elementWrapper.element) + ")";
        }
        if (elementWrapper.element.getParentElement() != null) {
            instance += ".setParent(" + getParent(elementWrapper) + ")";
        }
        instance += ";\n";
        return instance;
    }

    public String oneWordToProperCase(String string) {
        if (string.length() > 1) {
            return string.substring(0, 1).toUpperCase() + string.toLowerCase().substring(1, string.length());
        } else {
            return string.substring(0, 1).toUpperCase();
        }
    }

    public String stripIllegalCharacters(String string) {
        String legal = string;
        String[] illegalCharacters = new String[]{"!"};
        for (String illegalCharacter : illegalCharacters) {
            if (string.contains(illegalCharacter)) {
                legal = legal.replaceAll("!", "");
            }
        }
        return legal;
    }

    public Integer instanceCount(Element element) {
        if (elementQuantity.containsKey(element.getName())) {
            elementQuantity.put(element.getName(), elementQuantity.get(element.getName()) + 1);
        } else {
            elementQuantity.put(element.getName(), 1);
        }
        return elementQuantity.get(element.getName());
    }

    public String getParent(ElementWrapper elementWrapper) {
        if (elementWrapper.element.getDepth() == 0) {
            return null;
        } else {
            Element parent = elementWrapper.element.getParentElement();
            for (ElementWrapper elementWrapper1 : elementWrappers) {
                if (elementWrapper1.element.equals(parent)) {
                    return elementWrapper1.instanceName;
                }
            }
            return null;
        }
    }

    public String getClass(Element element) {
        String clazz = null;
        Attributes attributes = element.getAttributes();
        for (Attribute attribute : attributes) {
            if (attribute.getKey().equalsIgnoreCase("class")) {
                clazz = "new Clazz(\"";
                clazz += attribute.getValue();
                clazz += "\")";
                return clazz;
            }
        }
        return clazz;
    }

    public String getId(Element element) {
        String id = null;
        Attributes attributes = element.getAttributes();
        for (Attribute attribute : attributes) {
            if (attribute.getKey().equalsIgnoreCase("id")) {
                id = "new Id(\"";
                id += attribute.getValue();
                id += "\")";
                return id;
            }
        }
        return id;
    }

    //TODO: Specific attribute objects, i.e. "new Height(20, Unit.px)";
    public String getStyle(Element element) {
        String style = null;
        Attributes attributes = element.getAttributes();
        for (Attribute attribute : attributes) {
            if (attribute.getKey().equalsIgnoreCase("style")) {
                style = "new Style(\"";
                style += attribute.getValue();
                style += "\")";
                return style;
            }
        }
        return style;
    }

    public class ElementWrapper {

        public Element element;
        public String instanceName;

        public ElementWrapper(Element element, String instanceName) {
            this.element = element;
            this.instanceName = instanceName;
        }

        public ElementWrapper() {
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables
}

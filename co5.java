class Patient {
    int id;
    String name;
    int priority;

    Patient(int id, String name, int priority) {
        this.id = id;
        this.name = name;
        this.priority = priority;
    }
}

class AVLNode {
    Patient patient;
    AVLNode left, right;
    int height;

    AVLNode(Patient patient) {
        this.patient = patient;
        this.height = 1;
    }
}

public class HealthNetCO5AVLTree {
    AVLNode root;

    int height(AVLNode node) {
        return node == null ? 0 : node.height;
    }

    int getBalance(AVLNode node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    AVLNode insert(AVLNode node, Patient patient) {
        if (node == null)
            return new AVLNode(patient);

        if (patient.priority < node.patient.priority)
            node.left = insert(node.left, patient);
        else if (patient.priority > node.patient.priority)
            node.right = insert(node.right, patient);
        else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = getBalance(node);

        // LL
        if (balance > 1 && patient.priority < node.left.patient.priority)
            return rightRotate(node);

        // RR
        if (balance < -1 && patient.priority > node.right.patient.priority)
            return leftRotate(node);

        // LR
        if (balance > 1 && patient.priority > node.left.patient.priority) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL
        if (balance < -1 && patient.priority < node.right.patient.priority) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // Reverse inorder = highest priority first
    void reverseInorder(AVLNode node) {
        if (node != null) {
            reverseInorder(node.right);
            System.out.println("Patient ID: " + node.patient.id +
                    ", Name: " + node.patient.name +
                    ", Priority: " + node.patient.priority);
            reverseInorder(node.left);
        }
    }

    public static void main(String[] args) {
        HealthNetCO5AVLTree tree = new HealthNetCO5AVLTree();

        Patient[] patients = {
                new Patient(101, "Ravi", 70),
                new Patient(102, "Anu", 90),
                new Patient(103, "Kiran", 60),
                new Patient(104, "Meena", 95),
                new Patient(105, "Arjun", 80)
        };

        for (Patient p : patients) {
            tree.root = tree.insert(tree.root, p);
        }

        System.out.println("HealthNet – Emergency Patient Priority System");
        System.out.println("Patients in descending priority order:");
        tree.reverseInorder(tree.root);
    }
}
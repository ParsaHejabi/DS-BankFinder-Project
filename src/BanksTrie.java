public class BanksTrie {
    public int size;
    public boolean isLeaf;
    public KDTree kdTree;
    public BanksTrie[] banksTries;
    public String mostBranchedBank;
    public int championSize = 0;

    public BanksTrie() {
        size = 0;
        banksTries = new BanksTrie[26];
        isLeaf = false;
    }

    public void addBankInTrie(String name, BanksTrie root, Bank bank) {

        for (int level = 0; level < name.length(); level++) {
            int index = name.charAt(level) - 'a';
            if (root.banksTries[index] == null)
                root.banksTries[index] = new BanksTrie();

            root = root.banksTries[index];
        }
        root.isLeaf = true;
        if (root.kdTree == null){
            root.kdTree = new KDTree(false,null);
        }
        if (root.kdTree.leftChild == null && root.kdTree.rightChild == null && root.kdTree.bank == null){
            root.kdTree.bank = bank;
        }
        else{
            root.kdTree.add(root.kdTree, bank, false);
        }
        root.size++;
        if (root.size >= championSize){
            mostBranchedBank = name;
            championSize = root.size;
        }
    }

    public BanksTrie searchBankInTrie(String name, BanksTrie root) {

        for (int level = 0; level < name.length(); level++) {
            int index = name.charAt(level) - 'a';
            if (root.banksTries[index] == null)
                return null;

            root = root.banksTries[index];
        }

        if (root != null && root.isLeaf)
            return root;
        else
            return null;
    }

    public KDTree deleteBankInTrie(String name, BanksTrie root){

        BanksTrie searchedBankTrie = searchBankInTrie(name, root);

        if (searchedBankTrie.size == 1){
            searchedBankTrie.isLeaf = false;
        }
        searchedBankTrie.size--;

        return searchedBankTrie.kdTree.delete(searchedBankTrie.kdTree, searchedBankTrie.kdTree.bank.x, searchedBankTrie.kdTree.bank.y, false);
    }

}
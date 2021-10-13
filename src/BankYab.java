public class BankYab {

    public KDTree allBanks = new KDTree(false, null);
    public BanksTrie banksTrie = new BanksTrie();
    public DistrictsTrie districtsTrie = new DistrictsTrie();

    public void addDistrict(String districtName, double x1, double y1, double x2, double y2) {
        District district = new District(districtName, x1, y1, x2, y2);
        String districtNameLowerCase = districtName.toLowerCase();
        districtsTrie.addDistrictInTrie(districtNameLowerCase, districtsTrie, district);
//        second way
//        DistrictsTrie temp = new DistrictsTrie();
//        districtsTrie.districtsTries[districtNameLowerCase.charAt(0)-'a'] = temp;
//        for (int i=1; i<districtName.length(); i++){
//            temp.districtsTries[districtNameLowerCase.charAt(i)-'a']= new DistrictsTrie();
//            temp = temp.districtsTries[districtNameLowerCase.charAt(i)-'a'];
//            if (i == districtName.length()-1){
//                temp.isLeaf = true;
//                temp.district = new District(districtName, x1, y1, x2, y2);
//            }
//        }
    }

    public void addBank(String bankName, double x, double y) {
        Bank bank = new Bank(bankName, x, y);
        String bankNameLowerCase = bankName.toLowerCase();
        if (allBanks.leftChild == null && allBanks.rightChild == null && allBanks.bank == null) {
            allBanks.bank = bank;
            banksTrie.addBankInTrie(bankNameLowerCase, banksTrie, bank);
            System.out.println(bankName + " Bank added.");
            System.out.println("Coordinates: [" + x + "," + y + "]");
        } else {
            KDTree temp = allBanks.add(allBanks, bank, false);
            if (temp != null) {
                allBanks = temp;
                banksTrie.addBankInTrie(bankNameLowerCase, banksTrie, bank);
                System.out.println(bankName + " Bank added.");
                System.out.println("Coordinates: [" + x + "," + y + "]");
            } else {
                System.err.println("We can't add " + bankName + " bank. Another bank with this coordinates already exists!");
                System.err.println("Coordinates: [" + x + "," + y + "]");

            }
        }
    }

    public void deleteBank(double x, double y) {
        KDTree temp = allBanks.delete(allBanks, x, y, false);
        allBanks.flag = true;
        if (temp == null) {
            System.err.println("There is no bank with this coordinates to delete!");
        } else {
            allBanks = temp;
            Bank bank = allBanks.searchedBank;
            banksTrie.deleteBankInTrie(bank.name, banksTrie);
            System.out.println("Bank is deleted.");
        }
    }

    public void listOfDistrictBanks(String districtName) {
        String districtNameLowerCase = districtName.toLowerCase();
        District district = districtsTrie.searchDistrictInTrie(districtNameLowerCase, districtsTrie);
        if (district == null) {
            System.err.println(districtName + " district does not exist!");
        } else {
            System.out.println("Following banks are in the district:");
            allBanks.rangeSearch(allBanks, false, district.x1, district.y1, district.x2, district.y2);
        }
    }

    public void branchesOfBank(String bankName) {
        String bankNameLowerCase = bankName.toLowerCase();
        BanksTrie bank = banksTrie.searchBankInTrie(bankNameLowerCase, banksTrie);
        if (bank == null) {
            System.err.println(bankName + " bank does not exist!");
        } else {
            System.out.println("Coordinates of " + bankName + " bank branches:");
            bank.kdTree.inOrderTraverse(bank.kdTree);
        }
    }

    public void nearestBank(double x, double y) {
        KDTree nearestBank = allBanks.nearestNeighbourSearch(allBanks, x, y, false, allBanks);
        System.out.println("Nearest bank to your location is: " + nearestBank.bank.name + " bank.");
        System.out.println("Coordinates: [" + nearestBank.bank.x + "," + nearestBank.bank.y + "]");
    }

    public void accessibleBanks(double R, double x, double y) {
        System.out.println("Following banks are in the circle:");
        allBanks.rangeSearch(allBanks, false, R, x, y);
    }

    public void nearestBranchOfBank(String bankName, double x, double y) {
        String bankNameLowerCase = bankName.toLowerCase();
        BanksTrie bank = banksTrie.searchBankInTrie(bankNameLowerCase, banksTrie);
        if (bank == null) {
            System.err.println(bankName + " bank does not exist!");
        } else {
            KDTree nearestBank = allBanks.nearestNeighbourSearch(bank.kdTree, x, y, false, allBanks);
            System.out.println("Coordinates of nearest " + nearestBank.bank.name + " bank to your location is: ");
            System.out.println("[" + nearestBank.bank.x + "," + nearestBank.bank.y + "]");
        }
    }

    public void mostBranchedBank() {
        System.out.println("Most branched bank is: " + banksTrie.mostBranchedBank + " with " + banksTrie.championSize + " branches.");
    }
}
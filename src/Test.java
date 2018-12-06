public class Test {
    public static void main(String[] args) {
        BankYab bankYab = new BankYab();
        bankYab.addDistrict("darros", 0,0,7,15);
        //error ba 2 esme yeksan
        bankYab.addDistrict("darros", 3,2,4,4);
        bankYab.addDistrict("farmanie", 5,3,9,10);

        bankYab.addBank("shahr", 3,4);
        bankYab.addBank("mellat", 2,3);
        bankYab.addBank("mellat", 1,5);
        bankYab.addBank("shahr",9,1);
        bankYab.addBank("ayande",5,5);
        bankYab.addBank("melli",10,10);
        //error ba 2 mokhtasate yeksan
        bankYab.addBank("farda",3,4);

        bankYab.deleteBank(3,3);
        bankYab.mostBranchedBank();
        bankYab.listOfDistrictBanks("darros");
        bankYab.branchesOfBank("ayande");
        bankYab.nearestBank(2.5,2.5);
        bankYab.accessibleBanks(5,0,0);
        bankYab.nearestBranchOfBank("ayande", 2, 2);
        bankYab.nearestBranchOfBank("ayande", 6, 6);
    }
}
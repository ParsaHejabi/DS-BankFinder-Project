public class DistrictsTrie {
    public boolean isLeaf;
    public DistrictsTrie[] districtsTries;
    public District district;

    public DistrictsTrie() {
        districtsTries = new DistrictsTrie[26];
        isLeaf = false;
    }

    public void addDistrictInTrie(String name, DistrictsTrie root, District district) {

        for (int level = 0; level < name.length(); level++) {
            int index = name.charAt(level) - 'a';
            if (root.districtsTries[index] == null)
                root.districtsTries[index] = new DistrictsTrie();

            root = root.districtsTries[index];
        }
        if (root.isLeaf == true) {
            System.err.println("Change the district name!");
            System.err.println(name + " District exists already!");
        } else {
            root.isLeaf = true;
            root.district = district;
            System.out.println(root.district.name + " District added.");
            System.out.println("Coordinates: [" + root.district.x1 + "," + root.district.y1 + "] -- [" + root.district.x2 + "," + root.district.y2 + "]");
        }
    }

    public District searchDistrictInTrie(String name, DistrictsTrie root) {

        for (int level = 0; level < name.length(); level++) {
            int index = name.charAt(level) - 'a';
            if (root.districtsTries[index] == null)
                return null;

            root = root.districtsTries[index];
        }

        if (root != null && root.isLeaf)
            return root.district;
        else
            return null;
    }
}
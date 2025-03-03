package org.hiedacamellia.camellialib.client.debug;

import org.hiedacamellia.camellialib.client.gui.tree.DebugTreeEntryWidget;

import java.util.ArrayList;
import java.util.List;

public class DebugRegistries {

    private static List<DebugTreeEntryWidget> roots = new ArrayList<>();

    public static void registerRoot(DebugTreeEntryWidget root){
        roots.add(root);
    }

    public static List<DebugTreeEntryWidget> getRoots(){
        return roots;
    }

}

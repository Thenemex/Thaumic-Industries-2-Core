package thaumicindustries2core.asm;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.Name("TCI2Core")
@IFMLLoadingPlugin.SortingIndex(1001) // loading it late
@IFMLLoadingPlugin.TransformerExclusions("thaumicindustries2core.asm")
public class TCI2CorePlugin implements IFMLLoadingPlugin {

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{"thaumicindustries2core.asm.ClaimMapTransformer"};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}

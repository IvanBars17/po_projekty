public class SelectableMap extends VoivodeshipMap {
    private String selectedVoivodeship = null;

    public void select(String voivodeshipName) {
        this.selectedVoivodeship = voivodeshipName;
    }

    @Override
    protected String getColor(String voivodeship) {
        if (voivodeship.equalsIgnoreCase(selectedVoivodeship)) {
            return "#FF0000"; // Wybrane województwo na czerwono
        }
        return super.getColor(voivodeship); // Reszta ze standardowego koloru (szary)
    }
}

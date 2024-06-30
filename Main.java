import java.util.ArrayList;
import java.util.List;

// Класи шаблонів білдерів
class Building {
    private String foundation;
    private String structure;
    private String roof;

    public void setFoundation(String foundation) {
        this.foundation = foundation;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public void setRoof(String roof) {
        this.roof = roof;
    }

    @Override
    public String toString() {
        return "Будiвля [Фундамент=" + foundation + ", Структура=" + structure + ", Дах=" + roof + "]";
    }
}

abstract class BuildingBuilder {
    protected Building building;

    public void createNewBuilding() {
        building = new Building();
    }

    public Building getBuilding() {
        return building;
    }

    public abstract void buildFoundation();

    public abstract void buildStructure();

    public abstract void buildRoof();
}

class ConcreteBuildingBuilder extends BuildingBuilder {
    public void buildFoundation() {
        building.setFoundation("Бетонний фундамент");
    }

    public void buildStructure() {
        building.setStructure("Бетонна структура");
    }

    public void buildRoof() {
        building.setRoof("Бетонний дах");
    }
}

class ConstructionEngineer {
    private BuildingBuilder buildingBuilder;

    public void setBuildingBuilder(BuildingBuilder builder) {
        this.buildingBuilder = builder;
    }

    public Building getBuilding() {
        return buildingBuilder.getBuilding();
    }

    public void constructBuilding() {
        buildingBuilder.createNewBuilding();
        buildingBuilder.buildFoundation();
        buildingBuilder.buildStructure();
        buildingBuilder.buildRoof();
    }
}

// Складені класи шаблонів
interface BuildingComponent {
    void showDetails();
}

class Room implements BuildingComponent {
    private String name;

    public Room(String name) {
        this.name = name;
    }

    @Override
    public void showDetails() {
        System.out.println("Kiмната: " + name);
    }
}

class BuildingComposite implements BuildingComponent {
    private List<BuildingComponent> components = new ArrayList<>();

    public void addComponent(BuildingComponent component) {
        components.add(component);
    }

    public void removeComponent(BuildingComponent component) {
        components.remove(component);
    }

    @Override
    public void showDetails() {
        for (BuildingComponent component : components) {
            component.showDetails();
        }
    }
}

// Класи шаблону "Ланцюг відповідальності"
abstract class RequestHandler {
    protected RequestHandler nextHandler;

    public void setNextHandler(RequestHandler handler) {
        nextHandler = handler;
    }

    public void handleRequest(String request) {
        if (nextHandler != null) {
            nextHandler.handleRequest(request);
        }
    }
}

class Architect extends RequestHandler {
    @Override
    public void handleRequest(String request) {
        if (request.equals("Змiна дизайну")) {
            System.out.println("Архiтектор обробляє запит на змiну дизайну.");
        } else {
            super.handleRequest(request);
        }
    }
}

class Engineer extends RequestHandler {
    @Override
    public void handleRequest(String request) {
        if (request.equals("Змiна структури")) {
            System.out.println("Iнженер обробляє запит на змiну структури.");
        } else {
            super.handleRequest(request);
        }
    }
}

class Contractor extends RequestHandler {
    @Override
    public void handleRequest(String request) {
        if (request.equals("Змiна будiвництва")) {
            System.out.println("Пiдрядник обробляє запит на змiну будiвництва.");
        } else {
            super.handleRequest(request);
        }
    }
}

// Основний клас для демонстрації паттернів
public class Main {
    public static void main(String[] args) {
        // Демонстрація шаблону білдера
        ConstructionEngineer engineer = new ConstructionEngineer();
        BuildingBuilder builder = new ConcreteBuildingBuilder();
        engineer.setBuildingBuilder(builder);
        engineer.constructBuilding();
        Building building = engineer.getBuilding();
        System.out.println(building);

        // Демонстрація складеного шаблону
        Room room1 = new Room("Вiтальня");
        Room room2 = new Room("Спальня");

        BuildingComposite house = new BuildingComposite();
        house.addComponent(room1);
        house.addComponent(room2);
        house.showDetails();

        // Демонстрація моделі ланцюга відповідальності
        RequestHandler architect = new Architect();
        RequestHandler engineerHandler = new Engineer();
        RequestHandler contractor = new Contractor();

        architect.setNextHandler(engineerHandler);
        engineerHandler.setNextHandler(contractor);

        architect.handleRequest("Змiна дизайну");
        architect.handleRequest("Змiна структури");
        architect.handleRequest("Змiна будівництва");
    }
}

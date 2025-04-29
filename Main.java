import java.util.*;


public class Main {

    static class Goal {
        String type;
        int durationInDays;

        Goal(String type, int durationInDays) {
            this.type = type;
            this.durationInDays = durationInDays;
        }
    }


    static class User {
        String name;
        int age;
        Goal goal;

        User(String name, int age, Goal goal) {
            this.name = name;
            this.age = age;
            this.goal = goal;
        }

        public void showUserInfo() {
            System.out.println("\n--- User Information ---");
            System.out.println("Name: " + name + ", Age: " + age);
            System.out.println("Goal: " + goal.type + " for " + goal.durationInDays + " days");
        }
    }


    static class DiabeticUser extends User {
        double bloodSugarLevel;

        DiabeticUser(String name, int age, Goal goal, double bloodSugarLevel) {
            super(name, age, goal);
            this.bloodSugarLevel = bloodSugarLevel;
        }

        @Override
        public void showUserInfo() {
            super.showUserInfo();
            System.out.println("Blood Sugar Level: " + bloodSugarLevel + " mg/dL");
        }
    }

    static class MealPlan {
        List<String> meals = new ArrayList<>();

        void createMealPlan(String goalType) {
            meals.clear();
            if (goalType.equalsIgnoreCase("Weight Loss")) {
                meals.add("Oats with fruits");
                meals.add("Grilled chicken salad");
                meals.add("Steamed vegetables and brown rice");
            } else if (goalType.equalsIgnoreCase("Muscle Gain")) {
                meals.add("Boiled eggs and milk");
                meals.add("Chicken breast with quinoa");
                meals.add("Protein shake and banana");
            } else {
                meals.add("Balanced diet with fruits and veggies");
            }
        }

        void showMeals() {
            System.out.println("\n--- Meal Plan ---");
            for (String meal : meals) {
                System.out.println("- " + meal);
            }
        }
    }

    static class ExercisePlan {
        List<String> exercises = new ArrayList<>();

        void createExercisePlan(String goalType) {
            exercises.clear();
            if (goalType.equalsIgnoreCase("Weight Loss")) {
                exercises.add("Jogging - 30 mins");
                exercises.add("Jumping jacks - 3 sets");
                exercises.add("Yoga - 20 mins");
            } else if (goalType.equalsIgnoreCase("Muscle Gain")) {
                exercises.add("Push-ups - 4 sets");
                exercises.add("Weight lifting - 5 sets");
                exercises.add("Deadlifts - 3 sets");
            } else {
                exercises.add("Stretching - 10 mins");
                exercises.add("Light cardio - 20 mins");
            }
        }

        void showExercises() {
            System.out.println("\n--- Exercise Plan ---");
            for (String ex : exercises) {
                System.out.println("- " + ex);
            }
        }
    }

    static class HealthPlanner {
        MealPlan mealPlan;
        ExercisePlan exercisePlan;

        HealthPlanner() {
            mealPlan = new MealPlan();
            exercisePlan = new ExercisePlan();
        }

        void generatePlans(User user) {
            mealPlan.createMealPlan(user.goal.type);
            exercisePlan.createExercisePlan(user.goal.type);
        }

        void displayPlans() {
            mealPlan.showMeals();
            exercisePlan.showExercises();
        }
    }

    static class ReminderThread extends Thread {
        private boolean running = true;

        public void stopReminder() {
            running = false;
        }

        @Override
        public void run() {
            try {
                while (running) {
                    System.out.println("\n[Reminder] Time for your scheduled meal or workout!");
                    Thread.sleep(10000);
                }
            } catch (InterruptedException e) {
                System.out.println("Reminder thread interrupted.");
            }
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your age: ");
        int age = getIntInput(scanner);

        System.out.print("Enter your goal (Weight Loss / Muscle Gain): ");
        String goalType = scanner.nextLine();

        System.out.print("Enter goal duration in days: ");
        int duration = getIntInput(scanner);

        System.out.print("Are you diabetic? (yes/no): ");
        String diabeticAnswer = scanner.nextLine().trim();

        User user;
        Goal goal = new Goal(goalType, duration);

        if (diabeticAnswer.equalsIgnoreCase("yes")) {
            System.out.print("Enter your blood sugar level (mg/dL): ");
            double sugarLevel = getDoubleInput(scanner);
            user = new DiabeticUser(name, age, goal, sugarLevel);
        } else {
            user = new User(name, age, goal);
        }


        HealthPlanner planner = new HealthPlanner();
        planner.generatePlans(user);
        user.showUserInfo();
        planner.displayPlans();

        ReminderThread reminder = new ReminderThread();
        reminder.start();

        System.out.println("\nPress Enter to stop reminders...");
        scanner.nextLine(); // Wait for Enter key
        reminder.stopReminder();
        reminder.interrupt();
    }

    private static int getIntInput(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    private static double getDoubleInput(Scanner scanner) {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid decimal number: ");
            }
        }
    }
}

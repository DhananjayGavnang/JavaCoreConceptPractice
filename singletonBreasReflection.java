public class App {
	public static void main(String[] args)
			throws CloneNotSupportedException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, FileNotFoundException, IOException, ClassNotFoundException {
		System.out.println("Hello World!");

		LazySingleton instance1 = LazySingleton.getInstance();
		//System.out.println(instance1.hashCode());

		// Serialize singleton object to a file.
		ObjectOutput out = new ObjectOutputStream(new FileOutputStream("singleton.ser"));
		out.writeObject(instance1);
		out.close();

		// Deserialize singleton object from the file
		ObjectInput in = new ObjectInputStream(new FileInputStream("singleton.ser"));
		LazySingleton instance2 = (LazySingleton) in.readObject();
		in.close();

		System.out.println("instance1 hashCode: " + instance1.hashCode());
		System.out.println("instance2 hashCode: " + instance2.hashCode());

		
		//break singleton through reflection
		LazySingleton reflectionInstance=null;		
		
		Constructor[] constructors = LazySingleton.class.getDeclaredConstructors();
		for (Constructor constructor : constructors) {
			constructor.setAccessible(true); 
			reflectionInstance = (LazySingleton) constructor.newInstance(); }
		 
		System.out.println(reflectionInstance.hashCode());
		

		/*
		 * LazySingleton instance2 = (LazySingleton) instance1.clone();
		 * 
		 * System.out.println(instance2.hashCode());
		 */
	}
}

public class MyClone implements Cloneable {
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}

public class LazySingleton extends MyClone implements Serializable {
	private static LazySingleton instance;
	private LazySingleton() {
		if (instance != null) {
			throw new IllegalStateException("object can't be create using reflection");
		}
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	protected Object readResolve() {
		return instance;
	}
	public static synchronized LazySingleton getInstance() {
		if (instance == null) {
			return instance = new LazySingleton();
		} else {
			return instance;
		}
	}
}

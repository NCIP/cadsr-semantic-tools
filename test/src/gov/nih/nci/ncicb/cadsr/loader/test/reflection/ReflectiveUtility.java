package gov.nih.nci.ncicb.cadsr.loader.test.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectiveUtility
{

    public static Method getMethod(Class objectClass, String methodName, String argumentType)
    {
        Method returnMethod = null;

        Method[] methods = objectClass.getMethods();
        int size = methods.length;

        for (int i = 0; i < size; i++)
        {
            Method method = methods[i];

            if (method.getName().equals(methodName))
            {

                Class[] args = method.getParameterTypes();
                int argsize = args.length;

                if (argsize == 1)
                {
                    Class arg = args[0];

                    if (arg.getName().equals(argumentType))
                    {
                        returnMethod = method;
                        break;
                    }
                }
            }
        }

        return returnMethod;
    }

    public static Method getDeclaredMethod(Class objectClass, String methodName, Class[] parameterTypes) throws ReflectiveException {
    	Method method = null;
    	
    	try {
			// uber-hack: Using Java reflection to call private static method in CashflowClient
	    	method = objectClass.getDeclaredMethod(methodName, parameterTypes);	// get method
	    	method.setAccessible(true);				// make method accessible
		} catch (NoSuchMethodException nse) {
			System.out.println("method '" + methodName + "' is not found");
			throw new ReflectiveException(nse);
		} catch (NullPointerException npe) {
			System.out.println("method name is null");
			throw new ReflectiveException(npe);
		} catch (SecurityException se) {
			System.out.println("method '" + methodName + "' is not accessible");
			throw new ReflectiveException(se);
		}
		
		return method;
    }
    
    /*
     * sets an object with the specified pattern, value and type.
     */
    public static void setObject(Object object,
                                 Object value,
                                 String methodname,
                                 String type)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            ClassNotFoundException, NoSuchMethodException
    {
        try
        {
            Class types[] = new Class[1];
            Object args[] = new Object[1];

            args[0] = value;

            if (object instanceof java.util.Collection && methodname.equals("add"))
            {
                types[0] = Class.forName("java.lang.Object");
            }
            else
            {
                types[0] = Class.forName(type);
            }

            Method method = object.getClass().getMethod(methodname, types);

            method.invoke(object, args);
        }
        catch (ClassNotFoundException ce)
        {
            throw new IllegalArgumentException("The parameter type for " + type +
                    " for method=" + methodname + " is not valid");
        }
    }

    /*
    * sets an object with the specified pattern, value and type.
    */
    public static void setObject(Object object,
                                 Object value,
                                 String methodname,
                                 Class type)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException
    {
        Class types[] = new Class[1];
        Object args[] = new Object[1];

        args[0] = value;
        types[0] = type;

        Method method = object.getClass().getMethod(methodname, types);

        method.invoke(object, args);
    }

    public static boolean validateClassName(String className)
    {
        boolean valid = false;

        try
        {
            Class.forName(className);
            valid = true;
        }
        catch (ClassNotFoundException e)
        {
            valid = false;
        }

        return valid;
    }

    public static void executeMethod(Method method, Object source, Object parameter)
            throws ReflectiveException
    {
        try
        {
            Object args[] = new Object[1];

            args[0] = parameter;

            method.invoke(source, args);
        }
        catch (IllegalAccessException e)
        {
            throw new ReflectiveException(e);
        }
        catch (IllegalArgumentException e)
        {
            throw new ReflectiveException(e);
        }
        catch (InvocationTargetException e)
        {
            throw new ReflectiveException(e);
        }
    }

    public static Integer[] toObjectArray(int[] array)
    {
        if (array == null)
        {
            return null;
        }

        Integer[] data = new Integer[array.length];
        for (int i = 0; i < array.length; i++)
        {
            data[i] = new Integer(array[i]);
        }

        return data;
    }

    public static Double[] toObjectArray(double[] array)
    {
        if (array == null)
        {
            return null;
        }

        Double[] data = new Double[array.length];
        for (int i = 0; i < array.length; i++)
        {
            data[i] = new Double(array[i]);
        }

        return data;
    }

    public static double[] toArray(Double[] array)
    {
        if (array == null)
        {
            return null;
        }

        double[] data = new double[array.length];
        for (int i = 0; i < array.length; i++)
        {
            double val = (array[i] == null) ? 0 : array[i].intValue();
            data[i] = val;
        }

        return data;
    }

    public static int[] toArray(Integer[] array)
    {
        if (array == null)
        {
            return null;
        }

        int[] data = new int[array.length];
        for (int i = 0; i < array.length; i++)
        {
            int val = (array[i] == null) ? 0 : array[i].intValue();
            data[i] = val;
        }

        return data;
    }
}
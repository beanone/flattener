package org.beanone.flattener;

import java.util.Map;

import org.beanone.flattener.api.Flattener;
import org.beanone.flattener.api.KeyStack;
import org.beanone.flattener.api.Unflattener;
import org.beanone.flattener.exception.FlattenerException;
import org.junit.Test;

public class ArrayUnflattenerTest extends UnflattenerTestBase {

	@Test(expected = IllegalArgumentException.class)
	public void testArrayUnflattenerWithNullRegistry() {
		new ArrayUnflattener(null);
	}

	@Test
	public void testUnflatObjectArray() throws Exception {
		final SimpleTestBean[] arrayOfBeans = { new SimpleTestBean(),
		        new SimpleTestBean() };
		test(arrayOfBeans);
	}

	@Test
	public void testUnflatPrimitiveArray() throws Exception {
		final int[] arr = { 10, 20, 30, 40 };
		test(arr);
	}

	@Test(expected = FlattenerException.class)
	public void testUnflatWithRflectionOperationExceptionOnCreateObject()
	        throws Exception {
		final Unflattener unflattener = createUnflattenerForExceptionTest();
		final int[] arr = { 10, 20, 30, 40 };
		final Flattener flattener = createFlattener();
		final KeyStack keyStack = new KeyStack();
		keyStack.push("something");
		unflattener.unflat(flattener.flat(arr), keyStack, Object.class);
	}

	private Unflattener createUnflattenerForExceptionTest() {
		final Unflattener unflattener = new AbstractUnflattener(
		        new FlattenerRegistryImpl()) {
			@Override
			protected Object doCreateObject(Map<String, String> flatted,
		            KeyStack keyStack, Class<?> clazz)
		            throws ReflectiveOperationException {
				throw new ClassNotFoundException();
			}

			@Override
			protected void doPopulate(Object object, String key, int suffixSize,
		            Object value) throws ReflectiveOperationException {
				// do nothing;
			}
		};
		return unflattener;
	}

	@Override
	protected Flattener createFlattener() {
		return new ArrayFlattener(new FlattenerRegistryImpl());
	}

	@Override
	protected AbstractUnflattener createUnflattener() {
		return new ArrayUnflattener(new FlattenerRegistryImpl());
	}
}

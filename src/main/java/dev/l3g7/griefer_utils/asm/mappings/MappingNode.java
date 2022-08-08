package dev.l3g7.griefer_utils.asm.mappings;

import java.util.ArrayList;

/**
 * Generated by SimpleMCMappings v0.1
 */
public abstract class MappingNode {

	public final String obfuscated;
	public final String unobfuscated;

	private MappingNode(String obfuscated, String unobfuscated) {
		this.obfuscated = obfuscated;
		this.unobfuscated = unobfuscated;
	}

	@Override
	public String toString() {
		return (Mappings.obfuscated && obfuscated != null) ? obfuscated : unobfuscated;
	}

	public static class Class extends MappingNode {

		private final String name;

		public Class(String unobfuscated, String obfuscated) {
			super(obfuscated, unobfuscated);
			this.name = null;
		}

		public Class(String name) {
			super(name, name);
			this.name = name;
		}

		public String asType() {
			if(name != null)
				return name;

			return "L" + this + ";";
		}

		@Override
		public String toString() {
			if(name != null)
				return name;

			return super.toString();
		}

		public ArrayList<Method> getMethods() {
			ArrayList<Method> methodList = new ArrayList<>();
			for(java.lang.reflect.Method method : getClass().getDeclaredMethods()) {
				if(method.getReturnType() == Method.class) {
					try {
						methodList.add((Method) method.invoke(this));
					} catch (Throwable e) {
						throw new RuntimeException(e);
					}
				}
			}
			return methodList;
		}
	}

	public static class Field extends MappingNode {

		public final Class owner;
		public final Class type;

		public Field(String obfuscated, String unobfuscated, Class owner, Class type) {
			super(obfuscated, unobfuscated);
			this.owner = owner;
			this.type = type;
		}

	}

	public static class Method extends MappingNode {

		public final Class owner;
		public final Class returnType;
		public final Class[] params;

		public Method(String obfuscated, String unobfuscated, Class owner, Class returnType, Class... params) {
			super(obfuscated, unobfuscated);
			this.owner = owner;
			this.returnType = returnType;
			this.params = params;
		}

	}
}

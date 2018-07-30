package me.virustotal.dynamicgui.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.reflect.TypeToken;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class ConfigurationSection {

	protected ConfigurationNode node;
	protected ConfigurationLoader<?> loader;
	
	public void save()
	{
		try 
		{
			this.loader.save(this.node);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public ConfigurationSection getConfigurationSection(String path)
	{
		ConfigurationSection section = new ConfigurationSection();
		section.node = this.node.getNode(this.parsePath(path));
		return section;
	}
	
	public Object get(String path)
	{
		return this.node.getNode(this.parsePath(path)).getValue();
	}
	
	public String getString(String path)
	{
		return this.node.getNode(this.parsePath(path)).getString();
	}
	
	public int getInt(String path)
	{
		return this.node.getNode(this.parsePath(path)).getInt();
	}
	
	public Long getLong(String path)
	{
		return this.node.getNode(this.parsePath(path)).getLong();
	}
	
	public Float getFloat(String path)
	{
		return this.node.getNode(this.parsePath(path)).getFloat();
	}
	
	public boolean getBoolean(String path)
	{
		return this.node.getNode(this.parsePath(path)).getBoolean();
	}
	
	public double getDouble(String path)
	{
		return this.node.getNode(this.parsePath(path)).getDouble();
	}
	
	public List<String> getStringList(String path)
	{
		try 
		{
			return this.node.getNode(this.parsePath(path)).getList(TypeToken.of(String.class));
		}
		catch (ObjectMappingException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Integer> getIntList(String path)
	{
		try 
		{
			return this.node.getNode(this.parsePath(path)).getList(TypeToken.of(Integer.class));
		} 
		catch (ObjectMappingException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Long> getLongList(String path)
	{
		try 
		{
			return this.node.getNode(this.parsePath(path)).getList(TypeToken.of(Long.class));
		} 
		catch (ObjectMappingException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Float> getFloatList(String path)
	{
		try 
		{
			return this.node.getNode(this.parsePath(path)).getList(TypeToken.of(Float.class));
		}
		catch (ObjectMappingException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Boolean> getBooleanList(String path)
	{
		try
		{
			return this.node.getNode(this.parsePath(path)).getList(TypeToken.of(Boolean.class));
		}
		catch (ObjectMappingException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Double> getDoubleList(String path)
	{
		try
		{
			return this.node.getNode(this.parsePath(path)).getList(TypeToken.of(Double.class));
		}
		catch (ObjectMappingException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public void set(String path, Object toSave)
	{
		this.node.getNode(this.parsePath(path)).setValue(toSave);
	}
	
	public List<String> getKeys()
	{	
		List<String> keys = new ArrayList<String>();
		this.node.getChildrenMap().keySet().forEach(n -> keys.add((String) n));
		return keys;
	}
	
	public boolean isEmpty()
	{
		return this.getKeys().size() == 0;
	}
	
	public boolean hasKey(String key)
	{
		return getKeys().contains(key);
	}
	
	private Object[] parsePath(String path)
	{
		Object[] ar = new Object[1];
		if(path.contains("."))
		{
			String[] split = path.split("\\.");
			ar = new Object[split.length];
			for(int i = 0; i < split.length; i++)
			{
				ar[i] = (Object) split[i];
			}
		}
		else
		{
			ar[0] = (Object) path;
		}
		return ar;
	}
}
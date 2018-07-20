package com.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.mapper.PropertyValueMapper;
import com.tmall.pojo.Product;
import com.tmall.pojo.Property;
import com.tmall.pojo.PropertyValue;
import com.tmall.pojo.PropertyValueExample;
import com.tmall.service.PropertyService;
import com.tmall.service.PropertyValueService;
@Service
public class PropertyValueImpl implements PropertyValueService {
	@Autowired
	PropertyValueMapper pvmapper;
	@Autowired
	PropertyService ptService;
	

	@Override
	public void update(PropertyValue pv) {
		pvmapper.updateByPrimaryKeySelective(pv);
	}

	@Override
	public List<PropertyValue> list(int pid) {
		PropertyValueExample example = new PropertyValueExample();
		example.createCriteria().andPidEqualTo(pid);
		List<PropertyValue> pvs = pvmapper.selectByExample(example);
		for (PropertyValue propertyValue : pvs) {
			Property pt	= ptService.get(propertyValue.getPtid());
			propertyValue.setProperty(pt);
		}
		return pvs;
	}

	@Override
	public void init(Product p) {
		List<Property> pts = ptService.list(p.getCid());
		for (Property property : pts) {
			System.out.println(property);
			PropertyValue pvPropertyValue = get(property.getId(), p.getId());
			if (null==pvPropertyValue) {
				pvPropertyValue = new PropertyValue();
				pvPropertyValue.setPid(p.getId());
				pvPropertyValue.setPtid(property.getId());
				pvmapper.insert(pvPropertyValue);
			}
		}
		
	}

	@Override
	public PropertyValue get(int ptid, int pid) {
		PropertyValueExample example = new PropertyValueExample();
		example.createCriteria().andPidEqualTo(pid).andPtidEqualTo(ptid);
		List<PropertyValue> pvs = pvmapper.selectByExample(example);
		if (pvs.isEmpty()) {
			return null;
		}
		return pvs.get(0);
	}

}

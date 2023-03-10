package com.ahom.hrms.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import com.ahom.hrms.Repository.AttendanceDetailsRepository;
import com.ahom.hrms.Repository.BasicEmployeeRepository;
import com.ahom.hrms.entities.AttendanceDetails;
import com.ahom.hrms.entities.BasicEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.ahom.hrms.dto.AttendanceDetailsDto;

import com.ahom.hrms.service.AttendanceDetailsService;

@Service
public class AttendanceDetailsServiceImpl implements AttendanceDetailsService {

	@Autowired
	AttendanceDetailsRepository attendanceDetailsRepository;
	@Autowired
	BasicEmployeeRepository basicEmployeeRepository;

	//save data
	@Override
	public void saveAttendanceDetails(AttendanceDetailsDto attendanceDetailsDto)
	{
		BasicEmployee basicEmployee = basicEmployeeRepository.findById(attendanceDetailsDto.getEmpId()).orElse(null);
		if (basicEmployee==null)
		{
			throw new RuntimeException("no employee present");
		}
		attendanceDetailsDto.setBasicEmployee(basicEmployee);
		attendanceDetailsRepository.save(attendanceDetailsdtotoAttendanceDetails(attendanceDetailsDto));

	}

	@Override
	public List<AttendanceDetailsDto> getAllAttendanceDetails(){

		List<AttendanceDetails> listAttendanceDetails= this.attendanceDetailsRepository.findAll();
		List<AttendanceDetailsDto> attendanceDetialsDtoList = listAttendanceDetails.stream().map(emp -> this.attendanceDetailstoattendanceDetailsDto(emp)).collect(Collectors.toList());
		return attendanceDetialsDtoList ;

	}	
	//delete data
//	public void deleteAttendanceDetails(int empId){
//
//		attendanceDetailsRepository.deleteById(empId);
//
//	}
	//update data
	@Override
	public AttendanceDetails updateAttendanceDetails(AttendanceDetails attendanceDetails,int id)
	{
//		AttendanceDetails details1 = attendanceDetailsRepository.findById(attendanceDetails.getEmpId()).orElse(null);
			BasicEmployee basicEmployee = basicEmployeeRepository.findById(attendanceDetails.getEmpId()).orElse(null);
		AttendanceDetails details=new AttendanceDetails();
		details.setFromDate(attendanceDetails.getFromDate());
		details.setToDate(attendanceDetails.getToDate());
		details.setBasicEmployee(basicEmployee);
		details.setId(attendanceDetails.getEmpId());

		attendanceDetailsRepository.save(details);
		return details;

	}



	public AttendanceDetails attendanceDetailsdtotoAttendanceDetails(AttendanceDetailsDto attendanceDetailsDto)
	{
		BasicEmployee basicEmployee=basicEmployeeRepository.findById(attendanceDetailsDto.getEmpId())
				.orElse(null);
		AttendanceDetails attendanceDetails=new AttendanceDetails();
		attendanceDetails.setBasicEmployee(basicEmployee);
		attendanceDetails.setId(attendanceDetailsDto.getEmpId());

		attendanceDetails.setFromDate(attendanceDetailsDto.getFromDate());
//		attendanceDetails.setSelectEmployee(attendanceDetailsDto.getSelectEmployee());

		attendanceDetails.setToDate(attendanceDetailsDto.getToDate());


		return attendanceDetails;
	}

	public AttendanceDetailsDto attendanceDetailstoattendanceDetailsDto(AttendanceDetails attendanceDetails)
	{
		AttendanceDetailsDto attendanceDetailsDto= new AttendanceDetailsDto();

		BasicEmployee basicEmployee=basicEmployeeRepository.findById(attendanceDetailsDto.getEmpId())
				.orElse(null);
		attendanceDetailsDto.setBasicEmployee(attendanceDetails.getBasicEmployee());
		attendanceDetailsDto.setFromDate(attendanceDetails.getFromDate());
		attendanceDetailsDto.setToDate(attendanceDetails.getToDate());
//		attendanceDetailsDto.setSelectEmployee(attendanceDetails.getSelectEmployee());
		


		return attendanceDetailsDto;
	}

}

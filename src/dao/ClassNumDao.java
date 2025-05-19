package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bean.ClassNum;
import bean.School;

public class ClassNumDao extends Dao {

	public ClassNum get(String class_num, School school) throws Exception {
		ClassNum classNum = new ClassNum();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;

		try {
			statement = connection.prepareStatement("select * from class_num where class_num = ? and school_cd = ?");
			statement.setString(1, class_num);
			statement.setString(2, school.getCd());

			rSet = statement.executeQuery();
			SchoolDao sDao = new SchoolDao();

			if (rSet.next()) {
				classNum.setClass_num(rSet.getString("class_num"));
				classNum.setSchool(sDao.get(rSet.getString("school_cd")));
			} else {
				classNum = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return classNum;
	}

	public List<String> filter(School school) throws Exception {
		List<String> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;

		try {
			statement = connection.prepareStatement("select * from class_num where school_cd = ? order by class_num");
			statement.setString(1, school.getCd());
			rSet = statement.executeQuery();

			while (rSet.next()) {
				list.add(rSet.getString("class_num"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return list;
	}

	public Map<String, String> filter2(School school) throws Exception {
	    Map<String, String> classToSchoolMap = new LinkedHashMap<>(); // 順序を保持するためにLinkedHashMapを使用
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    ResultSet rSet = null;
	    try {
	        statement = connection.prepareStatement("select class_num, school_cd from class_num where school_cd = ? order by class_num");
	        statement.setString(1, school.getCd());
	        rSet = statement.executeQuery();
	        while (rSet.next()) {
	            String classNum = rSet.getString("class_num");
	            String schoolCd = rSet.getString("school_cd");
	            classToSchoolMap.put(classNum, schoolCd);
	        }
	    } catch (Exception e) {
	        throw e;
	    } finally {
	        if (rSet != null) {
	            try {
	                rSet.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	    }
	    return classToSchoolMap;
	}

	public List<ClassNum> filter3(School school) throws Exception {
	    List<ClassNum> classInfoList = new ArrayList<>();
	    String sql = "SELECT CLASS_NUM.class_num, COUNT(STUDENT.no) AS student_count " +
	                 "FROM CLASS_NUM " +
	                 "LEFT JOIN STUDENT ON CLASS_NUM.class_num = STUDENT.class_num AND STUDENT.school_cd = ? " +
	                 "WHERE CLASS_NUM.school_cd = ? " +
	                 "GROUP BY CLASS_NUM.class_num " +
	                 "ORDER BY CLASS_NUM.class_num";

	    try (Connection connection = getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)) {

	        statement.setString(1, school.getCd()); // STUDENT.school_cd 用
	        statement.setString(2, school.getCd()); // CLASS_NUM.school_cd 用

	        try (ResultSet rSet = statement.executeQuery()) {
	            while (rSet.next()) {
	                ClassNum classNumBean = new ClassNum();

	                classNumBean.setClass_num(rSet.getString("class_num"));
	                classNumBean.setSchool(school);  // 渡されたSchoolオブジェクトをそのままセット
	                classNumBean.setStudentCount(rSet.getInt("student_count"));

	                classInfoList.add(classNumBean);
	            }
	        }
	    }

	    return classInfoList;
	}

	public List<String> findNames(String classNum) throws Exception {
	    List<String> list = new ArrayList<>();
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    ResultSet rSet = null;

	    try {
	        statement = connection.prepareStatement(
	            "SELECT s.name FROM class_num AS c " +
	            "JOIN student AS s ON s.class_num = c.class_num " +
	            "WHERE s.class_num = ?"
	        );
	        statement.setString(1, classNum);
	        rSet = statement.executeQuery();

	        while (rSet.next()) {
	            list.add(rSet.getString("name"));
	        }
	    } catch (Exception e) {
	        throw e;
	    } finally {
	        if (rSet != null) {
	            try {
	                rSet.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	    }

	    return list;
	}




	public boolean save(ClassNum classNum) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		boolean result = false;

		try {
			ClassNum existingClassNum = get(classNum.getClass_num(), classNum.getSchool());

			if (existingClassNum == null) {
				statement = connection.prepareStatement(
						"INSERT INTO class_num (class_num, school_cd) VALUES (?, ?)");
				statement.setString(1, classNum.getClass_num());
				statement.setString(2, classNum.getSchool().getCd());

				int affected = statement.executeUpdate();
				result = (affected > 0);
			} else {
				result = true;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return result;
	}

	public boolean save(ClassNum classNum, String newClassNum ,String school_cd) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		PreparedStatement statement2 = null;
		boolean result = false;
		boolean result2 = false;

		try {
//			System.out.println("元のclass_num = "+classNum.getClass_num());
			statement = connection.prepareStatement(
					"UPDATE class_num SET class_num = ? WHERE class_num = ? AND school_cd = ?");
			statement.setString(1, newClassNum);
			statement.setString(2, classNum.getClass_num());
			statement.setString(3, school_cd);

			int affected = statement.executeUpdate();
			result = (affected > 0);
			statement2 = connection.prepareStatement("UPDATE STUDENT SET CLASS_NUM = ? WHERE CLASS_NUM = ? ");
			statement2.setString(1, newClassNum);
			statement2.setString(2, classNum.getClass_num());

			int affected2 = statement2.executeUpdate();
//			System.out.println(affected2);
			result2 = (affected2 > 0);
			if (result2 == false){
				result = false ;
			}

		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return result;
	}
	public boolean delete(String classNumStr, String schoolCd) throws Exception {
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    boolean result = false;

	    try {
	        statement = connection.prepareStatement("DELETE from class_num where class_num = ? and school_cd = ?");
	        statement.setString(1, classNumStr);
	        statement.setString(2, schoolCd);

	        int affectedRows = statement.executeUpdate();
	        result = (affectedRows > 0);  // 更新された行数が1以上なら成功

	    } catch (Exception e) {
	        throw e;
	    } finally {
	        if (statement != null) {
	            statement.close();
	        }
	        if (connection != null) {
	            connection.close();
	        }
	    }
	    return result;
	}
}
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
		boolean result = false;

		try {
			statement = connection.prepareStatement(
					"UPDATE class_num SET class_num = ? WHERE class_num = ? AND school_cd = ?");
			statement.setString(1, newClassNum);
			statement.setString(2, classNum.getClass_num());
			statement.setString(3, school_cd);

			int affected = statement.executeUpdate();
			result = (affected > 0);
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
	public void delete (String classNumStr , String schoolCd)throws Exception{
		Connection connection = getConnection();
		PreparedStatement statement = null;
		try {
            statement = connection.prepareStatement("DELETE from class_num where class_num = ? and school_cd = ?");
            statement.setString(1, classNumStr);
            statement.setString(2, schoolCd);
            statement.executeUpdate();

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
	}
}
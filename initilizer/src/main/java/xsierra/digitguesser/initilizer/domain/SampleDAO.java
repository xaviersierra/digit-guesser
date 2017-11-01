package xsierra.digitguesser.initilizer.domain;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class SampleDAO {

	private static final String INSERT_SAMPLE = "INSERT INTO train_sample(id, image, label) VALUES (?, ?, ?)";
	private static final String ALL_SAMPLES = "SELECT id, image, label FROM train_sample";

	private JdbcTemplate template;

	@Autowired
	public SampleDAO(JdbcTemplate template) {
		super();
		this.template = template;
	}

	public void saveSamples(List<Sample> samples) {

		SampleParameterizedPreparedStatementSetter pss = new SampleParameterizedPreparedStatementSetter();
		template.batchUpdate(INSERT_SAMPLE, samples, 25, pss);
	}
	
	public List<Sample> readAllSamples(){
		PreparedStatementCreator psc = new SampleStatementCreator(ALL_SAMPLES);
		SampleRowMapper srm = new SampleRowMapper(); 
		List<Sample> samples = template.query(psc, srm);
		return samples;
	}

	class SampleStatementCreator implements PreparedStatementCreator {
		private String sql;

		public SampleStatementCreator(String sql) {
			this.sql = sql;
		}

		@Override
		public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			return connection.prepareStatement(this.sql);
		}
	};

	class SampleRowMapper implements RowMapper<Sample> {

		@Override
		public Sample mapRow(ResultSet rs, int rowNum) throws SQLException {
			Sample sample = new Sample();
			sample.setId(rs.getInt("id"));
			
			Array imageArray = rs.getArray("image");
			Object[] unMappedimage = (Object[]) imageArray.getArray();
			int[] image = Arrays.stream(unMappedimage).mapToInt(i -> ((Integer) i).intValue()).toArray();
			sample.setImage(image);
			
			sample.setLabel(rs.getInt("label"));
			return sample;
		}
	}

	class SampleParameterizedPreparedStatementSetter implements ParameterizedPreparedStatementSetter<Sample>{

		@Override
		public void setValues(PreparedStatement preparedStatement, Sample sample) throws SQLException {
			preparedStatement.setLong(1, sample.getId());

			Object[] image = Arrays.stream(sample.getImage()).mapToObj(i -> Integer.valueOf(i)).toArray();
			preparedStatement.setObject(2, image);
			preparedStatement.setInt(3, sample.getLabel());
		}
	};
}

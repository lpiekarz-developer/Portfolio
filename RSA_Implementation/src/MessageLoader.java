import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public class MessageLoader {

	private File f_;
	private BufferedReader br_;
	private SecureRandom secrand_;
	private Random random_;
	private boolean padding_;
	private int blocklen_;
	private byte[] block_;
	private byte[] solitary_;
	private ArrayList<String> msg_;

	public MessageLoader(String path_, ArrayList<String> pt) throws FileNotFoundException {

		f_ = new File(path_);
		br_ = new BufferedReader( new FileReader(f_) );
		blocklen_ = 64;	/*in bytes*/
		block_ = new byte[blocklen_];
		padding_ = false;
		solitary_ = new byte[1];
		secrand_ = new SecureRandom();
		random_ = new Random();
		msg_ = pt;
	}

	public void loadIntoBlocks() throws IOException {

		while(br_.ready()) {

			/* Amount of read signs stored in count_ */
			int count_ = 0;

			for(int i = 0; i < blocklen_; ++i) {
				if( br_.ready() ) {
					block_[i] = (byte)br_.read();
					count_++;
				}
			}

			if( count_ != blocklen_) {
				
				padding_ = true;
				
				if(blocklen_ - count_ == 1)
					block_[blocklen_ - 1] = (byte)(blocklen_ - count_) ;
				
				else {
					block_[blocklen_ - 1] = (byte)(blocklen_ - count_) ;

					
					byte[] randarr_ = new byte[blocklen_ - count_ - 1];
					
					do {
						secrand_.nextBytes(randarr_);
						for(int i=0; i< randarr_.length; ++i){
							randarr_[i] = (byte) Math.abs(randarr_[i]);
						}
						
					} while (randarr_.length < (blocklen_ - count_ - 1));
					
							
					for(int i = 0; i < blocklen_ - (count_ + 1); ++i) {

						block_[i + count_] = (byte) (random_.nextInt(50)+1);
					}	
					
				}	
			}

			msg_.add(new String(block_));
		}
	}

	public void showMessage() {
		
		for(String tab_ : msg_) {

			System.out.print(tab_);
		}
	}
	
	public boolean getFlag() {
		return padding_;
	}	

}


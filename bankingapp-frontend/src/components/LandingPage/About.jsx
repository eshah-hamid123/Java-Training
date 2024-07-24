import React from 'react';

function About() {
  return (
    <div className="site-section cta-big-image" id="about-section">
      <div className="container">
        <div className="row mb-5 justify-content-center">
          <div className="col-md-8 text-center">
            <h2 className="section-title mb-3" data-aos="fade-up">About Us</h2>
            <p className="lead" data-aos="fade-up" data-aos-delay="100">Lorem ipsum dolor sit amet consectetur adipisicing elit. Minus minima neque tempora reiciendis.</p>
          </div>
        </div>
        <div className="row">
          <div className="col-lg-6 mb-5" data-aos="fade-up">
            <figure className="circle-bg">
              <img src="images/hero_1.jpg" alt="Free Website Template by Free-Template.co" className="img-fluid" />
            </figure>
          </div>
          <div className="col-lg-5 ml-auto" data-aos="fade-up" data-aos-delay="100">
            <h3 className="text-black mb-4">We Solve Your Financial Problem</h3>
            <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean.</p>
            <p>A small river named Duden flows by their place and supplies it with the necessary regelialia. It is a paradisematic country, in which roasted parts of sentences fly into your mouth.</p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default About;
